package com.nisum.user.api.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BaseException extends Exception{

	private static final long serialVersionUID = 8612006324442947569L;
	private final String errorDescription;
	private final HttpStatus statusCode;

	protected BaseException() {
		super();
		this.errorDescription = null;
		this.statusCode = null;
	}

	protected BaseException(String message) {
		super(message);
		this.errorDescription = null;
		this.statusCode = null;
	}

	protected BaseException(String message, HttpStatus statusCode) {
		super(message);
		this.errorDescription = null;
		this.statusCode = statusCode;
	}

	protected BaseException(String message, String errorDescription) {
		super(message);
		this.statusCode = null;
		this.errorDescription = errorDescription;
	}

	protected BaseException(String message, String errorDescription, Throwable cause) {
		super(message, cause);
		this.statusCode = null;
		this.errorDescription = errorDescription;
	}

	protected BaseException(String message, String errorDescription, HttpStatus statusCode) {
		super(message);
		this.errorDescription = errorDescription;
		this.statusCode = statusCode;
	}
	
	protected String getMessageAndDescription() {
		if (this.errorDescription != null && !this.errorDescription.isBlank()) {
			return this.getMessage() + " - " + this.getErrorDescription();
		} else {
			return this.getMessage();
		}
	}

}
