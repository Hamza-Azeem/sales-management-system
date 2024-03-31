package com.project.salesmanagementsystem.mapper;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.model.Client;

import java.util.stream.Collectors;

public class ClientMapper {

    public static ClientDTO mapToClientDto(Client client){
        ClientDTO clientDTO = ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .lastName(client.getLastName())
                .mobile(client.getMobile())
                .email(client.getEmail())
                .address(client.getAddress())
//                .password(client.getPassword())
//                .authorities(client.getAuthorities().stream().collect(Collectors.toList()))
                .build();
        return clientDTO;
    }
    public static Client mapToClient(ClientDTO clientDTO){
        Client client = Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .lastName(clientDTO.getLastName())
                .mobile(clientDTO.getMobile())
                .email(clientDTO.getEmail())
                .address(clientDTO.getAddress())
//                .password(clientDTO.getPassword())
//                .authorities(clientDTO.getAuthorities().stream().collect(Collectors.toSet()))
                .build();
        return client;
    }
}
