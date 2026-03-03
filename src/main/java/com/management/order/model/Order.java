package com.management.order.model;

import java.time.LocalDateTime;

public class Order {

    private Long id;
    private String customerName;
    private OrderType type;
    private double amount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(Long id, String customerName, OrderType type, double amount) {
        this.id = id;
        this.customerName = customerName;
        this.type = type;
        this.amount = amount;
        this.status = OrderStatus.NEW;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public OrderType getType() { return type; }
    public double getAmount() { return amount; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}