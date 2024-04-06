package com.project.salesmanagementsystem.service.Impl;

import com.project.salesmanagementsystem.DTO.ProductDTO;
import com.project.salesmanagementsystem.error.InvalidInputException;
import com.project.salesmanagementsystem.error.ObjectNotFoundException;
import com.project.salesmanagementsystem.model.Product;
import com.project.salesmanagementsystem.repository.ProductRepository;
import com.project.salesmanagementsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.salesmanagementsystem.mapper.ProductMapper.mapToProduct;
import static com.project.salesmanagementsystem.mapper.ProductMapper.mapToProductDto;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll() != null ? productRepository.findAll().stream()
                .map(product -> mapToProductDto(product)).collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        if(productDTO.getId() != null){
            throw new InvalidInputException("Invalid use of save method, Try update?");
        }
        Product product = productRepository.save(mapToProduct(productDTO));
        return mapToProductDto(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        if(productDTO.getId() == null){
            throw new InvalidInputException("Invalid use of update method, Try save?");
        }
        Optional<Product> productOptional = productRepository.findById(productDTO.getId());
        if(productOptional.isEmpty()){
            throw new ObjectNotFoundException("No product record with id=%s was found!".formatted(productDTO.getId()));
        }
        Timestamp creationDate = productOptional.get().getCreationDate();
        Product product = mapToProduct(productDTO);
        product.setCreationDate(creationDate);
        return mapToProductDto(productRepository.save(product));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO findProductById(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ObjectNotFoundException("No product record with id=%s was found!".formatted(id));
        }
        return mapToProductDto(productOptional.get());
    }
}
