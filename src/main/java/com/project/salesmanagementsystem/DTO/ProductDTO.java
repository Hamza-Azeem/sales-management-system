package com.project.salesmanagementsystem.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class ProductDTO {
    private Long id;
    @NotEmpty
    // Add constrain on length of name
    private String name;
    @NotEmpty
    // Add constrain on length of desc
    private String description;
    @NotEmpty
    private String category;
    private Timestamp creationDate;
    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer availableQuantity;
    @Min(value = 0)
    @NotNull
    private Double price;
}
