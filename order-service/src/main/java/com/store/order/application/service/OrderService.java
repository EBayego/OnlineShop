package com.store.order.application.service;

import com.store.order.adapter.out.messaging.OrderEventProducer;
import com.store.order.domain.exceptions.OrderCreationException;
import com.store.order.domain.exceptions.OrderNotFoundException;
import com.store.order.domain.exceptions.OrderUpdateException;
import com.store.order.domain.model.Order;
import com.store.order.domain.model.OrderItem;
import com.store.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

	@Autowired
    private OrderEventProducer orderEventProducer;

    @Autowired
    private OrderRepository orderRepository;
    
    
    public Order createOrder(Order order) {
        try {
            order.setStatus("CREATED");
            Order savedOrder = orderRepository.save(order);

            List<OrderItem> orderItems = order.getOrderItems(); 
            orderEventProducer.sendOrderMsg("reduceStock", savedOrder.getId(), orderItems);

            return savedOrder;
        } catch (Exception e) {
            throw new OrderCreationException(e.getMessage());
        }
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.setStatus(status);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new OrderUpdateException(orderId, status);
        }
    }

    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }
}