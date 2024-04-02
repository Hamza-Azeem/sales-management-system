package com.project.salesmanagementsystem.service.Impl;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.model.Client;
import com.project.salesmanagementsystem.repository.ClientRepository;
import com.project.salesmanagementsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static com.project.salesmanagementsystem.mapper.ClientMapper.mapToClient;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid client email"));
        return new User(client.getEmail(), client.getPassword(), client.getRoles());
    }
    public boolean isClientPresent(String email){
        return clientRepository.existsByEmail(email);
    }
    public ClientDTO saveClient(ClientDTO clientDTO){
        Client client = mapToClient(clientDTO);
        client.addRole(roleRepository.findByAuthority("USER"));
        clientRepository.save(client);
        return clientDTO;
    }


}
