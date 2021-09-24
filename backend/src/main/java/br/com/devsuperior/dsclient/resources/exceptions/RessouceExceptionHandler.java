package br.com.devsuperior.dsclient.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.devsuperior.dsclient.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class RessouceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError se = new StandardError();
		se.setMessage(e.getMessage());
		se.setTimestamp(Instant.now());
		se.setStatus(status.value());
		se.setError("Database violation");
		se.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(se);
	}
}
