package com.project.salesmanagementsystem.service.Impl;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.DTO.LocationStatDTO;
import com.project.salesmanagementsystem.DTO.LoginDTO;
import com.project.salesmanagementsystem.error.InvalidInputException;
import com.project.salesmanagementsystem.error.ObjectNotFoundException;
import com.project.salesmanagementsystem.model.Client;
import com.project.salesmanagementsystem.repository.ClientRepository;
import com.project.salesmanagementsystem.service.ClientService;
import com.project.salesmanagementsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.salesmanagementsystem.mapper.ClientMapper.mapToClient;
import static com.project.salesmanagementsystem.mapper.ClientMapper.mapToClientDto;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService, UserDetailsService {
    private final ClientRepository clientRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<ClientDTO> findAllClients() {
        return clientRepository.findAll() != null ? clientRepository.findAll().stream()
                .map(client -> mapToClientDto(client)).collect(Collectors.toList()) : Collections.emptyList();
    }
    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        if(isClientPresent(clientDTO.getEmail())){
            throw new InvalidInputException("Email is connected to another account!");
        }
        if(clientDTO.getId() != null){
            throw new InvalidInputException("Invalid use of save method, Try update?");
        }
        clientDTO.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        Client client = mapToClient(clientDTO);
        client.addRole(roleService.findByName("ROLE_USER"));
        clientRepository.save(client);
        return mapToClientDto(client);
    }
    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        if(clientDTO.getId() == null){
            throw new InvalidInputException("Invalid use of update method, Try save?");
        }
        Optional<Client> optionalClient = clientRepository.findById(clientDTO.getId());
        if(optionalClient.isEmpty()){
            throw new ObjectNotFoundException("No client record with id=%s was found!".formatted(clientDTO.getId()));
        }
        clientDTO.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        Client client = clientRepository.save(mapToClient(clientDTO));
        return mapToClientDto(client);
    }
    @Override
    public void deleteClientById(long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public ClientDTO findClientById(long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if(optionalClient.isEmpty()){
            throw new ObjectNotFoundException("No client record with id=%s was found!".formatted(id));
        }
        return mapToClientDto(optionalClient.get());
    }
    @Override
    public List<LocationStatDTO> findLocationStats() {
        List<Object[]> queryResult = clientRepository.findClientAddressStats();
        List<LocationStatDTO> locationStats = new ArrayList<>();
        for (Object[] row : queryResult) {
            long count = (Long) row[0];
            String address = (String) row[1];
            LocationStatDTO locationStatDTO = new LocationStatDTO(address, count);
            locationStats.add(locationStatDTO);
        }
        return locationStats;
    }
    @Override
    public boolean isClientPresent(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Email not found!")
        );
        return new User(client.getEmail(), client.getPassword(), client.getRoles());
    }
}
