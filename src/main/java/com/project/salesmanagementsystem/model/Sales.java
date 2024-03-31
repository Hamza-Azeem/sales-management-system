package com.project.salesmanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@Table(name = "sales")
@AllArgsConstructor
@NoArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Timestamp creationDate;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    @JoinTable(name = "product_sales",
    joinColumns = @JoinColumn(name = "sales_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;
    private Double total;
    public double getTotal(){
        double sum = 0;
        for(Product product : products){
            sum += product.getPrice();
        }
        return sum;
    }
    public void addToProducts(Product product){
        if(products == null){
            products = new HashSet<>();
        }
        products.add(product);
    }
}
