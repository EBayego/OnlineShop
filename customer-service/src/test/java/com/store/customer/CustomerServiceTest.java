package com.store.customer;

import com.store.customer.application.service.CustomerService;
import com.store.customer.domain.exceptions.CustomerNotFoundException;
import com.store.customer.domain.model.Customer;
import com.store.customer.domain.repository.CustomerRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testCreateCustomerSuccess() {
        Customer customer = new Customer(1L, "user1", "user1@example.com", LocalDate.of(1990, 1, 1));
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);
        Assertions.assertEquals("user1", createdCustomer.getUsername());
        Assertions.assertEquals("user1@example.com", createdCustomer.getEmail());
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        Long customerId = 1L;
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }

    @Test
    public void testUpdateCustomerStatusSuccess() {
        Customer customer = new Customer(1L, "user1", "user1@example.com", LocalDate.of(1990, 1, 1));
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomerInfo(1L, "userUpdated", "ACTIVE", null);
        Assertions.assertEquals("user1", updatedCustomer.getUsername());
    }
}