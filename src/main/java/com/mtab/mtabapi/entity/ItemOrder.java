package com.mtab.mtabapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_orders")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    private Order order;



}
