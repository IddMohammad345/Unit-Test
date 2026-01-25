package com.idd.usersservice.exceptions;

import com.idd.usersservice.ui.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<Object>handleValidationExceptions(BindException e, WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }
}
