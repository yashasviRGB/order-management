package com.management.order.service;

import com.management.order.exception.OrderNotFoundException;
import com.management.order.model.*;
import com.management.order.repository.OrderRepository;
import com.management.order.util.FileLoggerUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final FileLoggerUtil logger;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public OrderService(OrderRepository repository, FileLoggerUtil logger) {
        this.repository = repository;
        this.logger = logger;
    }

    public Order createOrder(String customer, OrderType type, double amount) {
        Long id = idGenerator.getAndIncrement();
        Order order = new Order(id, customer, type, amount);
        repository.save(order);

        logger.log("Order Created: " + id);
        processOrder(order);

        return order;
    }

    @Async("orderExecutor")
    public void processOrder(Order order) {
        try {
            order.setStatus(OrderStatus.PROCESSING);
            Thread.sleep(2000); // simulate processing
            order.setStatus(OrderStatus.COMPLETED);
            logger.log("Order Completed: " + order.getId());
        } catch (Exception e) {
            order.setStatus(OrderStatus.FAILED);
        }
    }

    public Order getOrder(Long id) {
        Order order = repository.findById(id);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        return order;
    }

    public double getTotalAmount() {
        return repository.findAll()
                .stream()
                .mapToDouble(Order::getAmount)
                .sum();
    }

    public Map<OrderType, Long> getBuySellCount() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getType, Collectors.counting()));
    }

    public String getTopCustomer() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomerName,
                        Collectors.summingDouble(Order::getAmount)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No orders");
    }

    public Map<OrderStatus, Long> groupByStatus() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
    }
}