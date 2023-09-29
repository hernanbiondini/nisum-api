package com.nisum.user.api.exceptions;

public class NotFoundException extends BaseException {
	
	private static final long serialVersionUID = -1588069883431349553L;

	public NotFoundException(String message, String errorDescription) {
		super(message, errorDescription);
	}

	public NotFoundException(String message) {
		super(message);
	}
	
}
