package com.security.demo.apiSecurityHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler{

}
