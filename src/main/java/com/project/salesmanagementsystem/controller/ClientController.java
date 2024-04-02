package com.project.salesmanagementsystem.controller;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public List<ClientDTO> findAllClients(){
        return clientService.findAllClients();
    }
    @GetMapping("/{id}")
    public ClientDTO findClientById(@PathVariable long id){
        return clientService.findClientById(id);
    }
    @PostMapping
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO){
        return clientService.saveClient(clientDTO);
    }
    @PutMapping
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO){
        return clientService.updateClient(clientDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable long id){
        clientService.deleteClientById(id);
    }

}
