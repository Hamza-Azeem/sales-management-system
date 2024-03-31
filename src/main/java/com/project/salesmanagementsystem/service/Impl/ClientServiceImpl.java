package com.project.salesmanagementsystem.service.Impl;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.DTO.LocationStatDTO;
import com.project.salesmanagementsystem.error.InvalidInputException;
import com.project.salesmanagementsystem.error.ObjectNotFoundException;
import com.project.salesmanagementsystem.model.Client;
import com.project.salesmanagementsystem.repository.ClientRepository;
import com.project.salesmanagementsystem.service.ClientService;
import lombok.RequiredArgsConstructor;
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
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    @Override
    public List<ClientDTO> findAllClients() {
        return clientRepository.findAll() != null ? clientRepository.findAll().stream()
                .map(client -> mapToClientDto(client)).collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        if(clientDTO.getId() != null){
            throw new InvalidInputException("Invalid use of save method, Try update?");
        }
        Client client = clientRepository.save(mapToClient(clientDTO));
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
}
