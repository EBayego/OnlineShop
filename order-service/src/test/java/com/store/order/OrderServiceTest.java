package com.store.order;

import com.store.order.application.service.OrderService;
import com.store.order.domain.exceptions.OrderNotFoundException;
import com.store.order.domain.model.Order;
import com.store.order.domain.repository.OrderRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrderSuccess() {
        Order order = new Order(1L, 1L, "CREATED", List.of(1L, 2L));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);
        Assertions.assertEquals("CREATED", createdOrder.getStatus());
    }

    @Test
    public void testGetOrderByIdNotFound() {
        Long orderId = 1L;
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderById(orderId);
        });
    }

    @Test
    public void testUpdateOrderStatusSuccess() {
        Order order = new Order(1L, 1L, "CREATED", List.of(1L, 2L));
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        Order updatedOrder = orderService.updateOrderStatus(1L, "SHIPPED");
        Assertions.assertEquals("SHIPPED", updatedOrder.getStatus());
    }
}