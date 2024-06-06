package com.wanted.market.controller;

import com.wanted.market.dto.ProductDto;
import com.wanted.market.dto.ProductNameDto;
import com.wanted.market.dto.TransactionDto;
import com.wanted.market.entity.Status;
import com.wanted.market.service.ProductService;
import com.wanted.market.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final TransactionService transactionService;

    @Autowired
    public ProductController(ProductService productService, TransactionService transactionService) {
        this.productService = productService;
        this.transactionService = transactionService;
    }

    // 비회원용 제품 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ProductNameDto>> getAllProductNames() {
        List<ProductNameDto> products = productService.getAllProductNames();
        return ResponseEntity.ok(products);
    }

    // 비회원용 제품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(product);
    }

    // 회원용 제품 등록
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.saveProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // 구매자가 제품 구매 요청
    @PostMapping("/{id}/buy")
    public ResponseEntity<TransactionDto> buyProduct(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
        ProductDto product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if(!transactionDto.getStatus().equals(Status.ON_SALE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        transactionDto.setStatus(Status.RESERVED);
        TransactionDto savedTransaction = transactionService.saveTransaction(transactionDto);
        productService.updateProductStatus(id, Status.RESERVED);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    // 판매자와 구매자가 당사자간의 거래 내역 조회
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getPreviousTransactions(@PathVariable Long id) {
        List<TransactionDto> transactions = transactionService.getPreviousTransactions(id);
        return ResponseEntity.ok(transactions);
    }

    // 모든 사용자가 구매한 용품 목록 조회
    @GetMapping("/bought/{buyerId}")
    public ResponseEntity<List<TransactionDto>> getBoughtTransactionsByBuyerId(@PathVariable Long buyerId) {
        List<TransactionDto> transactions = transactionService.getBoughtTransactionsByBuyerId(buyerId);
        return ResponseEntity.ok(transactions);
    }

    // 모든 사용자가 예약중인 용품 목록 조회
    @GetMapping("/reserved/{memberId}")
    public ResponseEntity<List<TransactionDto>> getReservedTransactionsByMemberId(@PathVariable Long memberId) {
        List<TransactionDto> transactions = transactionService.getReservedTransactionsByMemberId(memberId);
        return ResponseEntity.ok(transactions);
    }

    // 판매자가 거래 승인
    @PostMapping("/{id}/approve")
    public ResponseEntity<TransactionDto> approveTransaction(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
        ProductDto product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        productService.updateProductStatus(id, Status.SOLD_OUT);
        transactionDto.setStatus(Status.SOLD_OUT);
        TransactionDto savedTransaction = transactionService.saveTransaction(transactionDto);
        return ResponseEntity.ok(savedTransaction);
    }
}