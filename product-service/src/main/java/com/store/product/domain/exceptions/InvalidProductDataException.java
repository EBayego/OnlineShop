package com.store.product.domain.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidProductDataException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4387428182947222514L;

	public InvalidProductDataException(String message) {
        super(message);
    }
}