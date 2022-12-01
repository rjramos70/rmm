package com.ninjaone.rmm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ninjaone.rmm.exception.types.EntityConflictException;
import com.ninjaone.rmm.exception.types.EntityNotFoundException;
import com.ninjaone.rmm.exception.types.ErrorResponse;

@ControllerAdvice
public class GlobalHandleException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { EntityConflictException.class })
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<ErrorResponse> dulicateEntityException(EntityConflictException ex, WebRequest request) {
		return buildErrorResponse(ex, HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> entityNotException(EntityNotFoundException ex, WebRequest request) {
		return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
	}
	
	private ResponseEntity<ErrorResponse> buildErrorResponse(
		      Exception exception,
		      HttpStatus httpStatus,
		      WebRequest request
		  ) {
		    return buildErrorResponse(
		        exception, 
		        exception.getMessage(), 
		        httpStatus, 
		        request);
		  }
	
	private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus,
			WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message, exception.getCause());

		return ResponseEntity.status(httpStatus).body(errorResponse);
	}

}
