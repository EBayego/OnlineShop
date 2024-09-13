package com.store.product.domain.model;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
	
}