package com.pase.transport.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(
	        ResourceNotFoundException ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            "NOT_FOUND",
	            ex.getMessage(),
	            request.getRequestURI());

	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body(error);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(
	        BusinessException ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            "BUSINESS_ERROR",
	            ex.getMessage(),
	            request.getRequestURI());

	    return ResponseEntity.badRequest()
	            .body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(
	        MethodArgumentNotValidException ex,
	        HttpServletRequest request) {

	    String message = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error ->
	                    error.getField() + ": "
	                    + error.getDefaultMessage())
	            .findFirst()
	            .orElse("Validation error");

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            "VALIDATION_ERROR",
	            message,
	            request.getRequestURI());

	    return ResponseEntity.badRequest()
	            .body(error);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(
	        Exception ex,
	        HttpServletRequest request) {

	    log.error("Error interno", ex);

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.INTERNAL_SERVER_ERROR.value(),
	            "INTERNAL_SERVER_ERROR",
	            "Unexpected error occurred",
	            request.getRequestURI());

	    return ResponseEntity.status(
	            HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(error);
	}
}
