package com.wanted.market.service;

import com.wanted.market.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    TransactionDto saveTransaction(TransactionDto transactionDto);

    List<TransactionDto> getPreviousTransactions(Long productId);
    List<TransactionDto> getBoughtTransactionsByBuyerId(Long buyerId);
    List<TransactionDto> getReservedTransactionsByMemberId(Long memberId);
}
