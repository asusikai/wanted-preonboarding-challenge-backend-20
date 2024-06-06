package com.wanted.market.dto;

import com.wanted.market.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private Status status;
    private Long member_id;
}
