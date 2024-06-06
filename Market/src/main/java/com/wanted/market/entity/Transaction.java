package com.wanted.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member seller;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member buyer;

    @NonNull
    private Status status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Transaction() {}
}
