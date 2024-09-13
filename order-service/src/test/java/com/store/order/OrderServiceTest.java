package com.store.order;

import com.store.order.application.service.OrderService;
import com.store.order.domain.model.Order;
import com.store.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        // Arrange
        Order order = new Order();
        order.setStatus("CREATED");

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order createdOrder = orderService.createOrder(order);

        // Assert
        assertEquals("CREATED", createdOrder.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testUpdateOrderStatus() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setStatus("CREATED");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order updatedOrder = orderService.updateOrderStatus(1L, "SHIPPED");

        // Assert
        assertEquals("SHIPPED", updatedOrder.getStatus());
        verify(orderRepository, times(1)).save(order);
    }
}