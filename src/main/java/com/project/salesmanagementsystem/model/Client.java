package com.project.salesmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
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
//    @NotEmpty
//    @Size(min = 8, max = 50)
//    private String password;
    @NotEmpty
    @Size(min = 3, max = 100)
    private String address;
    @OneToMany(mappedBy = "client", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Sales> salesList;
    public void addSale(Sales sales){
        if(salesList == null){
            salesList = new HashSet<>();
        }
        salesList.add(sales);
    }

}
