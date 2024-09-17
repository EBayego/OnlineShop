package com.store.customer.domain.exceptions;

public class CustomerCreationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6611372967673886622L;

	public CustomerCreationException(String message) {
        super("Error al crear el cliente: " + message);
    }
}