package com.store.product.application.controller;

import com.store.product.adapter.out.messaging.ProductEventProducer;
import com.store.product.application.service.ProductService;
import com.store.product.domain.model.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Productos", description = "API para gestionar productos")
public class ProductController {

	@Autowired
	private ProductEventProducer producer;

    @Autowired
    private ProductService productService;

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public Product createproduct(@RequestBody Product product) {
    	this.producer.sendProductMsg(product.toString());
        return productService.createProduct(product);
    }

    @Operation(summary = "Obtener un producto por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getproductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public List<Product> getproducts() {
    	this.producer.sendProductMsg("Este mensaje es la prueba de kafka al retornar la lista de todos los products");
        return productService.getProducts();
    }

    @Operation(summary = "Actualizar el estado de un producto")
    @PutMapping("/{productId}/status")
    public Product updateproductStatus(@PathVariable String productId, @RequestParam String description, @RequestParam Double price, @RequestParam Integer stock) { //@RequestParam para la url
        return productService.updateProductInfo(productId, description, price, stock);
    }

    @Operation(summary = "Eliminar un producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteproduct(@PathVariable String id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}