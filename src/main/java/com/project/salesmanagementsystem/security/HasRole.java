package com.project.salesmanagementsystem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class HasRole {
    public boolean isAdmin(){
        var sec = SecurityContextHolder.getContext().getAuthentication();
        var auth = sec.getAuthorities();
        for(GrantedAuthority authority: auth){
            if(authority.getAuthority().equals("ROLE_ADMIN"))
                return true;
        }
        return false;
    }
}
