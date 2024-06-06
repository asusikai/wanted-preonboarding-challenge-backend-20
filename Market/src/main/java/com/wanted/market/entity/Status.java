package com.wanted.market.entity;

import lombok.Getter;

@Getter
public enum Status {
    ON_SALE("판매중"),
    SOLD_OUT("판매완료"),
    RESERVED("판매예약중");

    private final String status;
    Status(String status) {
        this.status = status;
    }
}
