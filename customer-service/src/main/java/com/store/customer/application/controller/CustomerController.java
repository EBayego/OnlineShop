package com.store.customer.application.controller;

import com.store.customer.adapter.out.messaging.CustomerEventProducer;
import com.store.customer.application.service.CustomerService;
import com.store.customer.domain.model.Customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@Tag(name = "Clientes", description = "API para gestionar clientes")
public class CustomerController {

	@Autowired
	private CustomerEventProducer producer;

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Crear un nuevo cliente")
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
    	this.producer.sendCustomerMsg(customer.toString());
        return customerService.createCustomer(customer);
    }

    @Operation(summary = "Obtener un cliente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos los clientes")
    @GetMapping
    public List<Customer> getCustomers() {
    	this.producer.sendCustomerMsg("Este mensaje es la prueba de kafka al retornar la lista de todos los customers");
        return customerService.getCustomers();
    }

    @Operation(summary = "Actualizar el estado de un cliente")
    @PutMapping("/{customerId}/status")
    public Customer updateCustomerStatus(@PathVariable Long customerId, @RequestBody String status) { //@RequestParam para la url
        return customerService.updateCustomerStatus(customerId, status);
    }

    @Operation(summary = "Eliminar un cliente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}