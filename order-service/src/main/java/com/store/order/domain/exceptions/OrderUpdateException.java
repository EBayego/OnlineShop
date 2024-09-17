package com.store.order.domain.exceptions;

public class OrderUpdateException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4564212661202241508L;

	public OrderUpdateException(Long id, String status) {
        super("No se pudo actualizar el pedido con ID " + id + " al estado: " + status);
    }

}
