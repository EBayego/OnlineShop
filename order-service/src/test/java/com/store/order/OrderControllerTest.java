package com.store.order;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.store.order.application.service.OrderService;
import com.store.order.domain.exceptions.OrderNotFoundException;
import com.store.order.domain.model.Order;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGetOrderByIdNotFound() throws Exception {
        Long orderId = 1L;
        Mockito.when(orderService.getOrderById(orderId)).thenThrow(new OrderNotFoundException(orderId));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", orderId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pedido no encontrado con ID: " + orderId));
    }

    @Test
    public void testCreateOrderSuccess() throws Exception {
        Order order = new Order(1L, 1L, "CREATED", List.of(1L, 2L));
        Mockito.when(orderService.createOrder(Mockito.any(Order.class))).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"customerId\": 1, \"productosId\": [1, 2] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.customerId").value(order.getCustomerId()))
                .andExpect(jsonPath("$.status").value(order.getStatus()));
    }

    @Test
    public void testUpdateOrderStatusNotFound() throws Exception {
        Long orderId = 999999L;
        String newStatus = "SHIPPED";
        
        Mockito.when(orderService.updateOrderStatus(orderId, newStatus))
               .thenThrow(new OrderNotFoundException(orderId));

        mockMvc.perform(MockMvcRequestBuilders.put("/orders/{orderId}/status", orderId)
                .param("status", "TO DELETE"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pedido no encontrado con ID: " + orderId));
    }

    @Test
    public void testDeleteOrderNotFound() throws Exception {
        Long orderId = 1L;
        Mockito.doThrow(new OrderNotFoundException(orderId)).when(orderService).deleteOrderById(orderId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", orderId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pedido no encontrado con ID: " + orderId));
    }
}