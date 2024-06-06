package com.wanted.market.service;

import com.wanted.market.dto.TransactionDto;
import com.wanted.market.entity.Transaction;
import com.wanted.market.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;

    }

    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setProduct(transactionDto.getProduct());
        transaction.setBuyer(transactionDto.getBuyer());
        transaction.setSeller(transactionDto.getSeller());
        transaction.setStatus(transactionDto.getStatus());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    @Override
    public List<TransactionDto> getPreviousTransactions(Long productId) {
        try {
            Optional<Transaction> transactionOpt = transactionRepository.findByProductId(productId);

            if (transactionOpt.isPresent()) {
                Transaction transaction = transactionOpt.get();
                Long sellerId = transaction.getSeller().getId();
                Long buyerId = transaction.getBuyer().getId();

                List<Transaction> transactions = transactionRepository.findPreviousTransactions(sellerId, buyerId);

                return transactions.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while searching previous transactions", e);
        }
    }

    @Override
    public List<TransactionDto> getBoughtTransactionsByBuyerId(Long buyerId) {
            return transactionRepository.findByBuyerId(buyerId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getReservedTransactionsByMemberId(Long memberId) {
        return transactionRepository.findReservedByMemberId(memberId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setProduct(transaction.getProduct());
        dto.setBuyer(transaction.getBuyer());
        dto.setSeller(transaction.getSeller());
        dto.setStatus(transaction.getStatus());
        return dto;
    }
}
