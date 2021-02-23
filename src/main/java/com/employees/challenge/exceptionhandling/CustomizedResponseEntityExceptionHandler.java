package com.employees.challenge.exceptionhandling;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * CustomizedResponseEntityExceptionHandler class is for central exception
 * handling throw the application.
 *
 */
@RestControllerAdvice

public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
				ex.getMessage().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * handling exception caused by user trying to add new employee with duplicate
	 * email
	 * 
	 * @return response body timeStamp, message and description of the error with
	 *         HTTP status code of CONFLICT.
	 */
	@ExceptionHandler(DuplicateEmailException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public final ResponseEntity<Object> handleduplicateEmailException(DuplicateEmailException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(true));
		return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
	}

	/**
	 * handling exception caused by user trying to update inexistent employee new
	 * employee
	 * 
	 * @return response body timeStamp, message and description of the error with
	 *         HTTP status code of NOT_FOUND.
	 */
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handleEmployeeNotFound(EmployeeNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(true));
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * handling exception caused by user trying to pass parameters violates
	 * constrains.
	 * 
	 * @return response body timeStamp, message and description of the error with
	 *         HTTP status code of BAD_REQUEST.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
				ex.getMessage().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
				ex.getBindingResult().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
