package com.project.salesmanagementsystem.security;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class IsSameUser {
    @Autowired
    private ClientService clientService;
    public boolean isPassing(long id){
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        ClientDTO clientDTO = clientService.findClientById(id);
        if(clientDTO.getEmail().equals(principal)){
            return true;
        }
        return false;
    }
    public boolean isPassing(ClientDTO clientDTO){
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = clientService.findClientById(clientDTO.getId()).getEmail();
        if(email.equals(principal)){
            return true;
        }
        return false;
    }
}
