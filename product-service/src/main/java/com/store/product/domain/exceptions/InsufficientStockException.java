package com.store.product.domain.exceptions;

public class InsufficientStockException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6132768508578132180L;

	public InsufficientStockException(String message) {
        super(message);
    }
}