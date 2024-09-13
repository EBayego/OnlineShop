package com.store.product;

import com.store.product.application.service.ProductService;
import com.store.product.domain.exceptions.InvalidProductDataException;
import com.store.product.domain.exceptions.ProductNotFoundException;
import com.store.product.domain.model.Product;
import com.store.product.domain.repository.ProductRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testGetProductById_ProductExists() {
        Product product = new Product("1", "Laptop", "Laptop Dell", 1200.0, 5);
        Mockito.when(productRepository.findById("1")).thenReturn(Optional.of(product));
        
        Product result = productService.getProductById("1");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Laptop", result.getName());
    }

    @Test
    public void testGetProductById_ProductDoesNotExist() {
        Mockito.when(productRepository.findById("2")).thenReturn(Optional.empty());
        
        Exception exception = Assertions.assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById("2");
        });

        Assertions.assertEquals("Producto no encontrado con ID: 2", exception.getMessage());
    }

    @Test
    public void testCreateProduct_ValidProduct() {
        Product product = new Product("1", "Laptop", "Laptop Dell", 1200.0, 5);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        
        Product createdProduct = productService.createProduct(product);
        
        Assertions.assertNotNull(createdProduct);
        Assertions.assertEquals("Laptop", createdProduct.getName());
    }

    @Test
    public void testCreateProduct_InvalidProduct() {
        Product product = new Product("1", null, "Laptop Dell", 1200.0, 5);
        
        Exception exception = Assertions.assertThrows(InvalidProductDataException.class, () -> {
            productService.createProduct(product);
        });

        Assertions.assertEquals("El producto debe tener un nombre, precio y stock válidos.", exception.getMessage());
    }

    @Test
    public void testUpdateProduct_ProductExists() {
        Product product = new Product("1", "Laptop", "Laptop Dell", 1200.0, 5);
        Mockito.when(productRepository.findById("1")).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        
        Product updatedProduct = productService.updateProductInfo("1", "Laptop actualizada", 1500.0, 10);
        
        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals("Laptop actualizada", updatedProduct.getDescription());
        Assertions.assertEquals(1500.0, updatedProduct.getPrice());
        Assertions.assertEquals(10, updatedProduct.getStock());
    }

    @Test
    public void testDeleteProduct_ProductExists() {
        Mockito.when(productRepository.existsById("1")).thenReturn(true);
        Mockito.doNothing().when(productRepository).deleteById("1");
        
        productService.deleteProductById("1");
        
        Mockito.verify(productRepository, Mockito.times(1)).deleteById("1");
    }

    @Test
    public void testDeleteProduct_ProductDoesNotExist() {
        Mockito.when(productRepository.existsById("2")).thenReturn(false);
        
        Exception exception = Assertions.assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProductById("2");
        });

        Assertions.assertEquals("Producto no encontrado con ID: 2", exception.getMessage());
    }
}
