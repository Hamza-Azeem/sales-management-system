package com.project.salesmanagementsystem.service;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.DTO.LocationStatDTO;
import com.project.salesmanagementsystem.DTO.ProductDTO;

import java.util.List;

public interface ClientService {
    public List<ClientDTO> findAllClients();
    public ClientDTO saveClient(ClientDTO clientDTO);
    public ClientDTO updateClient(ClientDTO clientDTO);
    public void deleteClientById(long id);
    public ClientDTO findClientById(long id);
    public List<LocationStatDTO> findLocationStats();
}
