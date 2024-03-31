package com.project.salesmanagementsystem.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
@Builder
@Setter
@Getter
public class SalesDTO {
    private Long id;
    private Timestamp creationDate;
    private ClientDTO client;
    private Set<ProductDTO> products;
    private Double total;
}
