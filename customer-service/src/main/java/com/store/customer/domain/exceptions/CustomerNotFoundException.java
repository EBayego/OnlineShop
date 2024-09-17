package com.store.customer.domain.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8071012621533122028L;

	public CustomerNotFoundException(Long id) {
        super("Cliente con ID " + id + " no fue encontrado.");
    }
}