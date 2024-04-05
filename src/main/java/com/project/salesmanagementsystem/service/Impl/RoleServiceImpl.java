package com.project.salesmanagementsystem.service.Impl;

import com.project.salesmanagementsystem.model.Role;
import com.project.salesmanagementsystem.repository.RoleRepository;
import com.project.salesmanagementsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role findByName(String auth) {
        return roleRepository.findByAuthority(auth);
    }
}
