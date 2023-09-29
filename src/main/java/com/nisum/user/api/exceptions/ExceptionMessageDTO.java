package com.nisum.user.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionMessageDTO {
	private String timestamp;
	private int status;
	private String error;
	private String message;
	private String path;

}
