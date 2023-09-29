package com.nisum.user.api.exceptions;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	private static final String DEFAULT_PACKAGE = "nisum";

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
		ErrorResponseDTO res = handleBaseExceptionInternal(ex, request, HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
		ErrorResponseDTO res = handleBaseExceptionInternal(ex, request, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
	}

	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException ex,
			HttpServletRequest request) {
		ErrorResponseDTO res = handleBaseExceptionInternal(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		ErrorResponseDTO res = handleMethodArgumentNotValidExceptionInternal(ex, request, HttpStatus.BAD_REQUEST,
				"Validar el body del request.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
		ErrorResponseDTO res = handleExceptionInternal(ex, request, HttpStatus.BAD_REQUEST,
				"No existe el elemento buscado.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException ex,
			HttpServletRequest request) {
		ErrorResponseDTO res = handleExceptionInternal(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {
		ErrorResponseDTO res = handleExceptionInternal(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}

	private ErrorResponseDTO handleBaseExceptionInternal(BaseException ex, HttpServletRequest request,
			HttpStatus statusCode) {
		String exceptionFullMessage = this.getExceptionFullMessage(ex);
		log.error("Exception trace: \n{} \nException message: \n{}", exceptionFullMessage,
				ex.getMessageAndDescription());
		return ErrorResponseDTO.builder().timestamp(LocalDateTime.now()).httpStatus(statusCode)
				.message(ex.getMessageAndDescription()).error(exceptionFullMessage).path(request.getServletPath())
				.build();
	}

	private ErrorResponseDTO handleExceptionInternal(Exception ex, HttpServletRequest request, HttpStatus statusCode,
			String exceptionMessage) {
		String exceptionFullMessage = this.getExceptionFullMessage(ex);
		log.error("Exception trace: \n{} \nException message: \n{}", exceptionFullMessage, exceptionMessage);
		return ErrorResponseDTO.builder().timestamp(LocalDateTime.now()).httpStatus(statusCode)
				.message(exceptionMessage).error(exceptionFullMessage).path(request.getServletPath()).build();
	}

	private ErrorResponseDTO handleMethodArgumentNotValidExceptionInternal(MethodArgumentNotValidException ex,
			HttpServletRequest request, HttpStatus statusCode, String internalExceptionMessage) {
		String exceptionMessage = this.getMessageMethodArgumentNotValidException(ex);
		log.error("Exception trace: \n{}", exceptionMessage);
		return ErrorResponseDTO.builder().timestamp(LocalDateTime.now()).httpStatus(statusCode)
				.message(internalExceptionMessage).error(exceptionMessage).path(request.getServletPath()).build();
	}

	private String getMessageMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		StringBuilder sb = new StringBuilder("Validation failed for argument [")
				.append(ex.getParameter().getParameterIndex()).append("] ");
		BindingResult bindingResult = ex.getBindingResult();
		if (bindingResult.getErrorCount() > 1) {
			sb.append(" with ").append(bindingResult.getErrorCount()).append(" errors");
		}
		sb.append(": ");
		for (ObjectError error : bindingResult.getAllErrors()) {
			sb.append('[').append(error).append("] ");
		}
		return sb.toString();
	}

	private String getExceptionFullMessage(Exception ex) {
		StringBuilder sb = new StringBuilder();
		sb.append("Exception info: " + ex.toString());

		StackTraceElement[] stackEx = ex.getStackTrace();
		sb.append(stackEx.length > 0 ? "\nTrace:" : "");
		for (StackTraceElement traceElement : stackEx) {
			if (traceElement.getClassName().startsWith(DEFAULT_PACKAGE)) {
				sb.append("\n\tat " + traceElement);
			}
		}
		Throwable ourCause = ex.getCause();
		sb.append(ourCause != null ? "\nCaused by:" : "");
		getExceptionCauses(ourCause, sb);

		return sb.toString();
	}

	private String getExceptionCauses(Throwable ex, StringBuilder sb) {
		if (ex != null) {
			sb.append("\n\tCause: " + ex.toString());
			return getExceptionCauses(ex.getCause(), sb);
		} else {
			return sb.toString();
		}
	}

}