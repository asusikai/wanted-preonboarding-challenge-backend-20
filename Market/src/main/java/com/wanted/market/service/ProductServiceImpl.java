package com.wanted.market.service;

import com.wanted.market.dto.ProductDto;
import com.wanted.market.dto.ProductNameDto;
import com.wanted.market.entity.Member;
import com.wanted.market.entity.Product;
import com.wanted.market.entity.Status;
import com.wanted.market.repository.MemberRepository;
import com.wanted.market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<ProductNameDto> getAllProductNames() {
        return productRepository.findAll().stream()
                .map(product -> {
                    ProductNameDto productNameDto = new ProductNameDto();
                    productNameDto.setName(product.getName());
                    productNameDto.setStatus(product.getStatus());
                    return productNameDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDto).orElse(null);
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        Member member = memberRepository.findById(productDto.getMember_id())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + productDto.getMember_id()));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStatus(Status.ON_SALE);
        product.setMember(member);

        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public ProductDto updateProductStatus(Long id, Status status) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

            product.setStatus(status);
            Product updatedProduct = productRepository.save(product);
            return convertToDto(updatedProduct);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update the product status", e);
        }
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStatus(product.getStatus());
        dto.setMember_id(product.getMember().getId());
        return dto;
    }
}
