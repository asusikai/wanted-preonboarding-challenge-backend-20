package com.wanted.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private double price;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @NonNull
    private Status status = Status.ON_SALE;

    public Product(){}
}
