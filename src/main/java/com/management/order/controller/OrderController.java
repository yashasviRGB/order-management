package com.management.order.controller;

import com.management.order.model.Order;
import com.management.order.model.OrderStatus;
import com.management.order.model.OrderType;
import com.management.order.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('TRADER')")
    @PostMapping
    public Order createOrder(@RequestParam String customer,
                             @RequestParam OrderType type,
                             @RequestParam double amount) {
        return service.createOrder(customer, type, amount);
    }

    @PreAuthorize("hasAnyRole('ADMIN','VIEWER')")
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.getOrder(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/analytics/total")
    public double totalAmount() {
        return service.getTotalAmount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/analytics/type")
    public Map<?, ?> buySellCount() {
        return service.getBuySellCount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/analytics/top-customer")
    public String topCustomer() {
        return service.getTopCustomer();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/analytics/status")
    public Map<OrderStatus, Long> groupByStatus() {
        return service.groupByStatus();
    }
}