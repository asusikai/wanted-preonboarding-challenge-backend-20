package com.wanted.market.repository;

import com.wanted.market.dto.TransactionDto;
import com.wanted.market.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE (t.seller.id = :sellerId AND t.buyer.id = :buyerId OR " +
            "t.seller.id = :buyerId AND t.buyer.id = :sellerId)")
    List<Transaction> findPreviousTransactions(@Param("sellerId") Long sellerId,
                                               @Param("buyerId") Long buyerId);
    @Query("SELECT t FROM Transaction t WHERE t.product.id = :productId")
    Optional<Transaction> findByProductId(@Param("productId") Long productId);

    @Query("SELECT t FROM Transaction t WHERE t.buyer.id = :buyerId")
    List<Transaction> findByBuyerId(@Param("buyerId")Long buyerId);

    @Query("SELECT t FROM Transaction t WHERE t.buyer.id = :memberId OR t.seller.id = :memberId")
    List<Transaction> findReservedByMemberId(@Param("memberId")Long memberId);
}
