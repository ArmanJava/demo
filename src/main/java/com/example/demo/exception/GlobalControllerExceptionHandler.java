package com.example.demo.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleConflict(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ErrorInfo handleUnauthorized(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
}
