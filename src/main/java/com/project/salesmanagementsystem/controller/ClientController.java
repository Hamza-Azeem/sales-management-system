package com.project.salesmanagementsystem.controller;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.DTO.LoginDTO;
import com.project.salesmanagementsystem.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody ClientDTO clientDTO){
        clientService.saveClient(clientDTO);
        return ResponseEntity.ok("User saved successfully");

    }
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                        loginDTO.getPassword());
        authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return ResponseEntity.ok("You are logged in successfully");
    }

    @GetMapping
    @PreAuthorize("@hasRole.isAdmin()")
    public List<ClientDTO> findAllClients(){
        return clientService.findAllClients();
    }
    @GetMapping("/{id}")
    @PreAuthorize("@hasRole.isAdmin() || @isSameUser.isPassing(#id)")
    public ClientDTO findClientById(@PathVariable long id){
        return clientService.findClientById(id);
    }
    @PostMapping
    @PreAuthorize("@hasRole.isAdmin()")
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO){
        return clientService.saveClient(clientDTO);
    }
    @PutMapping
    @PreAuthorize("@hasRole.isAdmin() || @isSameUser.isPassing(#clientDTO)")
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO){
        return clientService.updateClient(clientDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@hasRole.isAdmin() || @isSameUser.isPassing(#id)")
    public void deleteClientById(@PathVariable long id){
        clientService.deleteClientById(id);
    }

}
