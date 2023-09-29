package com.nisum.user.api.exceptions;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Standard response for errors.
 * 
 * @author Fabrizio Sosa
 */
@JsonInclude(content = Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
	
	private LocalDateTime timestamp;
	private HttpStatus httpStatus;
	private String message;
	private String error;
	private String path;
	
	@JsonProperty
	public String getHttpStatus() {
		return httpStatus.value() + " - " + httpStatus.getReasonPhrase();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(httpStatus, message, path, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorResponseDTO other = (ErrorResponseDTO) obj;
		return Objects.equals(httpStatus, other.httpStatus) && Objects.equals(message, other.message)
				&& Objects.equals(path, other.path) && Objects.equals(timestamp, other.timestamp);
	}
	
}
