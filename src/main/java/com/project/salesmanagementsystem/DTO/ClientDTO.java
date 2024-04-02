package com.project.salesmanagementsystem.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class ClientDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String mobile;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String address;
    @NotEmpty
    @Size(min = 8, max = 150)
    private String password;
//    private List<Authority> authorities;
}
