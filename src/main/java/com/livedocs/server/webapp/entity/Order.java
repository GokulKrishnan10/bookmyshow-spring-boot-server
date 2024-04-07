package com.livedocs.server.webapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "date_of_order", nullable = false)
    private String doo;
}
