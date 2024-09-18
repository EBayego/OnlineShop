package com.store.product.application.service;

import com.store.product.domain.exceptions.InsufficientStockException;
import com.store.product.domain.exceptions.InvalidProductDataException;
import com.store.product.domain.exceptions.ProductNotFoundException;
import com.store.product.domain.model.Product;
import com.store.product.domain.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    
    public Product createProduct(Product product) {
        if (product.getName() == null || product.getPrice() == null || product.getStock() == null) {
            throw new InvalidProductDataException("El producto debe tener un nombre, precio y stock válidos.");
        }
        return productRepository.save(product);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProductInfo(String productId, String description, Double price, Integer stock) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        return productRepository.save(product);
    }

    public void deleteProductById(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
    
    public void reduceStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("No hay suficiente stock para el producto: " + product.getName());
        }

        // Reducir stock
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}