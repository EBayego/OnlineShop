package com.store.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.store.product.adapter.out.messaging.ProductEventProducer;
import com.store.product.application.controller.ProductController;
import com.store.product.application.service.ProductService;
import com.store.product.domain.model.Product;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    
    @MockBean
    private ProductEventProducer producer;

    @Test
    public void testGetProductById_ProductExists() throws Exception {
        Product product = new Product("1", "Laptop", "Laptop Dell", 1200.0, 5);
        Mockito.when(productService.getProductById("1")).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    public void testGetProductById_ProductDoesNotExist() throws Exception {
    	Mockito.when(productService.getProductById("2")).thenReturn(null);
        
        mockMvc.perform(get("/products/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct_Valid() throws Exception {
        Product product = new Product("1", "Laptop", "Laptop Dell", 1200.0, 5);
        Mockito.when(productService.createProduct(Mockito.any(Product.class))).thenReturn(product);
        
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Laptop\",\"description\":\"Laptop Dell\",\"price\":1200,\"stock\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }
}
