package com.store.customer.domain.exceptions;

public class CustomerUpdateException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5749292676844737748L;

	public CustomerUpdateException(Long id) {
        super("No se pudo actualizar el cliente con ID " + id);
    }
}