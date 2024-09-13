package com.store.order.domain.repository;

import com.store.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
	Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long id);
    Order updateStatus(Long id, String status);
}