package datahill.com.bankingAPITest.controller;

import datahill.com.bankingAPITest.exception.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchElementException.class})
    public void noSuchElement() {
        throw new ResourceNotFound("REQUESTED RESOURCE WAS NOT FOUND");
    }


}
