package com.inventory.exception;

import org.springframework.http.HttpStatus;

public class InventoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus status;

	public InventoryException() {
		super();
	}

	public InventoryException(String message, HttpStatus status) {
		super();
		this.status = status;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
