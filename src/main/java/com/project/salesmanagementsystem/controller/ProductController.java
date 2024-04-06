package com.project.salesmanagementsystem.controller;

import com.project.salesmanagementsystem.DTO.ProductDTO;
import com.project.salesmanagementsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@hasRole.isAdmin()")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){
        return productService.saveProduct(productDTO);
    }
    @PutMapping
    @PreAuthorize("@hasRole.isAdmin()")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@hasRole.isAdmin()")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }


}
