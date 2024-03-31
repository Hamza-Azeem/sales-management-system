package com.project.salesmanagementsystem.controller;

import com.project.salesmanagementsystem.DTO.ProductDTO;
import com.project.salesmanagementsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public List<ProductDTO> findAllProducts(){
        return productService.findAllProducts();
    }
    @PostMapping
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){
        return productService.saveProduct(productDTO);
    }
    @PutMapping
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }
    @DeleteMapping("/{id}")
    private void deleteProductById(@PathVariable long id){
        productService.deleteProductById(id);
    }


}
