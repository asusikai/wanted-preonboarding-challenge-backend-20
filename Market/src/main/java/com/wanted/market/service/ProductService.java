package com.wanted.market.service;

import com.wanted.market.dto.ProductDto;
import com.wanted.market.dto.ProductNameDto;
import com.wanted.market.entity.Product;
import com.wanted.market.entity.Status;

import java.util.List;

public interface ProductService {
    List<ProductNameDto> getAllProductNames();
    ProductDto getProductById(Long id);
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProductStatus(Long id, Status status);
}
