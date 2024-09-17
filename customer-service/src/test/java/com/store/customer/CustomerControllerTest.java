package com.store.customer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.store.customer.application.service.CustomerService;
import com.store.customer.domain.exceptions.CustomerNotFoundException;
import com.store.customer.domain.model.Customer;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetCustomerByIdNotFound() throws Exception {
        Long customerId = 1L;
        Mockito.when(customerService.getCustomerById(customerId)).thenThrow(new CustomerNotFoundException(customerId));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", customerId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente con ID " + customerId + " no fue encontrado."));
    }

    @Test
    public void testCreateCustomerSuccess() throws Exception {
        Customer customer = new Customer(1L, "user1", "user1@example.com", LocalDate.of(1990, 1, 1));
        Mockito.when(customerService.createCustomer(Mockito.any(Customer.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"user1\", \"email\": \"user1@example.com\", \"birthDate\": \"1990-01-01\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.username").value(customer.getUsername()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()));
    }

    @Test
    public void testUpdateCustomerInfoNotFound() throws Exception {
        Long customerId = 1L;
        String username = "newUsername";
        String email = "newemail@example.com";
        String birthDate = "1990-01-01";

        Mockito.when(customerService.updateCustomerInfo(customerId, username, email, LocalDate.parse(birthDate)))
               .thenThrow(new CustomerNotFoundException(customerId));

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{customerId}/status", customerId)
                .param("username", username)
                .param("email", email)
                .param("birthDate", birthDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente con ID " + customerId + " no fue encontrado."));
    }

    @Test
    public void testDeleteCustomerNotFound() throws Exception {
        Long customerId = 1L;
        Mockito.doThrow(new CustomerNotFoundException(customerId)).when(customerService).deleteCustomerById(customerId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", customerId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente con ID " + customerId + " no fue encontrado."));
    }
}