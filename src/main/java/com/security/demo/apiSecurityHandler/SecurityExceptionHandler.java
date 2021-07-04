package com.security.demo.apiSecurityHandler;

import java.time.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(EntidadeVaziaException.class)
	public ResponseEntity<Problem> handlerEntidadeNaoEncotradaException(EntidadeVaziaException ex,
			HttpServletRequest request){
		HttpStatus  status = HttpStatus.BAD_REQUEST;

		Problem problema = new Problem();
		problema.setStatus(status.value());
		problema.setData(OffsetDateTime.now());
		problema.setTitulo(ex.getLocalizedMessage());
		problema.setPath(request.getRequestURL().toString());
		
		return ResponseEntity.status(status).body(problema);
	}

}
