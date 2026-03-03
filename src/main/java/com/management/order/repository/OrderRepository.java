package com.management.order.repository;

import com.management.order.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {

    private final Map<Long, Order> orderStore = new ConcurrentHashMap<>();

    public void save(Order order) {
        orderStore.put(order.getId(), order);
    }

    public Order findById(Long id) {
        return orderStore.get(id);
    }

    public Collection<Order> findAll() {
        return orderStore.values();
    }
}