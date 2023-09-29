package com.nisum.user.api.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends BaseException {
	
	private static final long serialVersionUID = -3954351843741508841L;

	public BadRequestException(String message, String errorDescription) {
		super(message, errorDescription);
	}

	public BadRequestException(String message) {
		super(message);
	}

}
