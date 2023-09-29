package com.nisum.user.api.exceptions;

public class InternalServerErrorException extends BaseException {
	
	private static final long serialVersionUID = 5328345574549996003L;

	public InternalServerErrorException() {
		super();
	}
	
	public InternalServerErrorException(String message) {
		super(message);
	}

	public InternalServerErrorException(String message, String errorDescription) {
		super(message, errorDescription);
	}

}
