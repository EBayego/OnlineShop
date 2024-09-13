package com.store.order.application.controller;

import com.store.order.adapter.out.messaging.OrderEventProducer;
import com.store.order.application.service.OrderService;
import com.store.order.domain.model.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Tag(name = "Pedidos", description = "API para gestionar pedidos")
public class OrderController {

	@Autowired
	private OrderEventProducer producer;

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Crear un nuevo pedido")
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
    	this.producer.sendOrderMsg(order.toString());
        return orderService.createOrder(order);
    }

    @Operation(summary = "Obtener un pedido por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public List<Order> getOrders() {
    	this.producer.sendOrderMsg("Este mensaje es la prueba de kafka al retornar la lista de todos los orders");
        return orderService.getOrders();
    }

    @Operation(summary = "Actualizar el estado de un pedido")
    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestBody String status) { //@RequestParam para la url
        return orderService.updateOrderStatus(orderId, status);
    }

    @Operation(summary = "Eliminar un pedido por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}