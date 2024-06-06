package com.wanted.market.dto;

import com.wanted.market.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNameDto {
    private String name;
    private Status status;
}
