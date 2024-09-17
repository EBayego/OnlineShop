package com.store.customer.application.service;

import com.store.customer.domain.exceptions.CustomerCreationException;
import com.store.customer.domain.exceptions.CustomerNotFoundException;
import com.store.customer.domain.exceptions.CustomerUpdateException;
import com.store.customer.domain.model.Customer;
import com.store.customer.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer createCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new CustomerCreationException(e.getMessage());
        }
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomerInfo(Long customerId, String username, String email, LocalDate birthDate) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        try {
        	if (username.isEmpty())
        		customer.setUsername(username);
        	if (email.isEmpty())
        		customer.setEmail(email);
        	if (birthDate != null)
        		customer.setBirthDate(birthDate);
        	
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new CustomerUpdateException(customerId);
        }
    }

    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
    }
}