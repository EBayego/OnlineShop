package com.store.order.domain.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderCreationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3507097068593198718L;

	public OrderCreationException(String message) {
		super("Error al crear el pedido: " + message);
    }
}