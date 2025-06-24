package com.turan.exception;



public class BaseException extends RuntimeException {

    public BaseException(ErrorMessage errorMessage){
        super(errorMessage.generateException());
    }


}
