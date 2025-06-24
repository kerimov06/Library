package com.turan.exceptionhandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError<E> {


    private HttpStatus status;
    private ExceptionDetails<E> exception;
}
