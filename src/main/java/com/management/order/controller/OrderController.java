package com.management.order.controller;

import com.management.order.model.Order;
import com.management.order.model.OrderType;
import com.management.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestParam String customer,
                             @RequestParam OrderType type,
                             @RequestParam double amount) {
        return service.createOrder(customer, type, amount);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.getOrder(id);
    }

    @GetMapping("/analytics/total")
    public double totalAmount() {
        return service.getTotalAmount();
    }

    @GetMapping("/analytics/type")
    public Map<?, ?> buySellCount() {
        return service.getBuySellCount();
    }

    @GetMapping("/analytics/top-customer")
    public String topCustomer() {
        return service.getTopCustomer();
    }

    @GetMapping("/analytics/status")
    public Map<?, ?> groupByStatus() {
        return service.groupByStatus();
    }
}