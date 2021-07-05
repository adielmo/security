package com.security.demo.apiSecurityHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.security.demo.securityException.EntidadeNaoEncotradaException;
import com.security.demo.securityException.EntidadeVaziaException;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Problem.Campo> campos = new ArrayList<>();

		Problem problema = new Problem();
		problema.setStatus(status.value());
		problema.setData(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
		problema.setCampos(campos);

		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(EntidadeVaziaException.class)
	public ResponseEntity<Problem> handlerEntidadeVaziaEncotradaException(EntidadeVaziaException ex,
			HttpServletRequest request){
		HttpStatus  status = HttpStatus.BAD_REQUEST;

		Problem problema = new Problem();
		problema.setStatus(status.value());
		problema.setData(OffsetDateTime.now());
		problema.setTitulo(ex.getLocalizedMessage());
		problema.setPath(request.getRequestURL().toString());
		
		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(EntidadeNaoEncotradaException.class)
	public ResponseEntity<Problem> handlerEntidadeNaoEncotradaException(EntidadeNaoEncotradaException ex,
			HttpServletRequest request){
		HttpStatus  status = HttpStatus.NOT_FOUND;

		Problem problema = new Problem();
		problema.setStatus(status.value());
		problema.setData(OffsetDateTime.now());
		problema.setTitulo(ex.getLocalizedMessage());
		problema.setPath(request.getRequestURL().toString());
		
		return ResponseEntity.status(status).body(problema);
	}

}
