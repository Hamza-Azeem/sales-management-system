package com.project.salesmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;


@Setter
@Getter
@Builder
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String category;
    @CreationTimestamp
    private Timestamp creationDate;
    @Min(value = 0)
    @Max(value = 100)
    @NotNull
    @Column(name = "available_quantity")
    private int availableQuantity;
    @Min(value = 0)
    @NotNull
    private Double price;
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Sales> salesList;
}
