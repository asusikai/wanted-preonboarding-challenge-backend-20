package com.wanted.market.dto;

import com.wanted.market.entity.Member;
import com.wanted.market.entity.Product;
import com.wanted.market.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private Product product;
    private Member buyer;
    private Member seller;
    private Status status;
}
