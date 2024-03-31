package com.project.salesmanagementsystem.mapper;

import com.project.salesmanagementsystem.DTO.ProductDTO;
import com.project.salesmanagementsystem.model.Product;

public class ProductMapper {

    public static ProductDTO mapToProductDto(Product product){
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .availableQuantity(product.getAvailableQuantity())
                .category(product.getCategory())
                .description(product.getDescription())
                .creationDate(product.getCreationDate())
                .price(product.getPrice())
                .build();
        return productDTO;
    }
    public static Product mapToProduct(ProductDTO productDTO){
        Product product = Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .availableQuantity(productDTO.getAvailableQuantity())
                .category(productDTO.getCategory())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();
        return product;
    }
}
