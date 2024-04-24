package com.mysystem.productservice.service;

import com.mysystem.productservice.dto.ProductRequest;
import com.mysystem.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
