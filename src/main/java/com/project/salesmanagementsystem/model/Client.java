package com.project.salesmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;
    @NotEmpty
    @Column(name = "last_name")
    @Size(min = 3, max = 20)
    private String lastName;
    @NotEmpty
    private String mobile;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 8, max = 150)
    private String password;
    @NotEmpty
    @Size(min = 3, max = 100)
    private String address;
    @OneToMany(mappedBy = "client", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Sales> salesList;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
    public void addSale(Sales sales){
        if(salesList == null){
            salesList = new HashSet<>();
        }
        salesList.add(sales);
    }
    public void addRole(Role role){
        if(roles == null)
            roles = new ArrayList<>();
        roles.add(role);
    }

}
