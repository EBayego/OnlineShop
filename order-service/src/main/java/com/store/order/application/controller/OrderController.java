package com.store.order.application.controller;

import com.store.order.application.service.OrderService;
import com.store.order.domain.exceptions.OrderNotFoundException;
import com.store.order.domain.model.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Pedidos", description = "API para gestionar pedidos")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Crear un nuevo pedido")
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @Operation(summary = "Obtener un pedido por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public List<Order> getOrders() {
    	//this.producer.sendOrderMsg("Este mensaje es la prueba de kafka al retornar la lista de todos los orders");
        return orderService.getOrders();
    }

    @Operation(summary = "Actualizar el estado de un pedido")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Eliminar un pedido por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}