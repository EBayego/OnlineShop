package com.store.customer.application.controller;

import com.store.customer.adapter.out.messaging.CustomerEventProducer;
import com.store.customer.application.service.CustomerService;
import com.store.customer.domain.exceptions.CustomerUpdateException;
import com.store.customer.domain.model.Customer;
import com.store.customer.common.util.ConvertToLocalDate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
		Customer customer = customerService.getCustomerById(id);
		if (customer == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(customer);
	}

	@Operation(summary = "Listar todos los clientes")
	@GetMapping
	public List<Customer> getCustomers() {
		this.producer.sendCustomerMsg("Este mensaje es la prueba de kafka al retornar la lista de todos los customers");
		return customerService.getCustomers();
	}

	@Operation(summary = "Actualizar el estado de un cliente")
	@PutMapping("/{customerId}/status")
	public Customer updateCustomerStatus(@PathVariable Long customerId, @RequestParam String username,
			@RequestParam String email, @RequestParam String birthDate) {
		LocalDate birthDateObj = null;
		try {
			birthDateObj = ConvertToLocalDate.stringToLocalDate(birthDate);
		} catch (Exception e) {
			throw new CustomerUpdateException(customerId);
		}
		return customerService.updateCustomerInfo(customerId, username, email, birthDateObj);
	}

	@Operation(summary = "Eliminar un cliente por su ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomerById(id);
		return ResponseEntity.noContent().build();
	}
}