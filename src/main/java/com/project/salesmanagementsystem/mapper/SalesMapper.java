package com.project.salesmanagementsystem.mapper;

import com.project.salesmanagementsystem.DTO.SalesDTO;
import com.project.salesmanagementsystem.model.Sales;

import java.util.stream.Collectors;

import static com.project.salesmanagementsystem.mapper.ClientMapper.mapToClient;
import static com.project.salesmanagementsystem.mapper.ClientMapper.mapToClientDto;
import static com.project.salesmanagementsystem.mapper.ProductMapper.mapToProduct;
import static com.project.salesmanagementsystem.mapper.ProductMapper.mapToProductDto;

public class SalesMapper {

    public static SalesDTO mapToSalesDto(Sales sales){
        SalesDTO salesDTO = SalesDTO.builder()
                .id(sales.getId())
                .creationDate(sales.getCreationDate())
                .client(sales.getClient() != null ? mapToClientDto(sales.getClient()):null)
                .products(sales.getProducts() != null ? sales.getProducts().stream()
                        .map(product -> mapToProductDto(product)).collect(Collectors.toSet()) : null)
                .total(sales.getTotal())
                .build();
        return salesDTO;
    }
    public static Sales mapToSales(SalesDTO salesDTO){
        Sales sales = Sales.builder()
                .id(salesDTO.getId())
//                .creationDate(salesDTO.getCreationDate())
                .client(salesDTO.getClient() != null ? mapToClient(salesDTO.getClient()):null)
                .products(salesDTO.getProducts() != null ? salesDTO.getProducts().stream()
                        .map(productDTO -> mapToProduct(productDTO)).collect(Collectors.toSet()) : null)
                .total(salesDTO.getTotal())
                .build();
        return sales;
    }

}
