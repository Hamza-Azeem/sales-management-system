package com.project.salesmanagementsystem.service;

import com.project.salesmanagementsystem.DTO.ProductDTO;



import java.util.List;

public interface ProductService {
    public List<ProductDTO> findAllProducts();
    public ProductDTO saveProduct(ProductDTO productDTO);
    public ProductDTO updateProduct(ProductDTO productDTO);
    public void deleteProductById(long id);
    public ProductDTO findProductById(long id);
}
