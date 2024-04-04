package com.project.salesmanagementsystem.controller;

import com.project.salesmanagementsystem.DTO.ClientDTO;
import com.project.salesmanagementsystem.DTO.LoginDTO;
import com.project.salesmanagementsystem.DTO.ResponseDTO;
import com.project.salesmanagementsystem.security.JWTGenerator;
import com.project.salesmanagementsystem.service.Impl.AuthenticationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ClientDTO clientDTO){
        if(authenticationService.isClientPresent(clientDTO.getEmail())){
            return new ResponseEntity<>("An account is registered with this email already!", HttpStatus.BAD_REQUEST);
        }
        clientDTO.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        ClientDTO client = authenticationService.saveClient(clientDTO);
        return ResponseEntity.ok("User saved successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        ResponseDTO responseDTO = new ResponseDTO(token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }











}
