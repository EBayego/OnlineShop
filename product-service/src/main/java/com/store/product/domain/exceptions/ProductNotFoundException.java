package com.store.product.domain.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8397671408724329455L;

	public ProductNotFoundException(String id) {
        super("Producto no encontrado con ID: " + id);
    }
}