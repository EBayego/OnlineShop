package com.store.order.domain.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6897842131643760270L;

	public OrderNotFoundException(Long id) {
        super("Pedido no encontrado con ID: " + id);
    }
}