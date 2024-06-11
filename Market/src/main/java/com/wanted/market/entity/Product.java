package com.wanted.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ON_SALE;

    public Product(String name, double price, Member member, Status status) {
        this.name = name;
        this.price = price;
        this.member = member;
        this.status = status;
    }

    public void updateStatus(final Status status) {
        this.status = status;
    }
}
