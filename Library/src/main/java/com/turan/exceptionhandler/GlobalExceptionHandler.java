package com.turan.exceptionhandler;

import com.turan.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<?>> exceptionHandler(BaseException exception, WebRequest request){
         return ResponseEntity.badRequest().body(apiError(exception.getMessage(),request));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<Map<String,List<String>>>> validException(MethodArgumentNotValidException exception , WebRequest request){
           Map<String , List<String>> map = new HashMap<>();

            for (ObjectError obj : exception.getBindingResult().getAllErrors()){
              String fieldName =  ((FieldError)obj).getField();

              if (map.containsKey(fieldName)){
                   map.put(fieldName,addValue(map.get(fieldName),obj.getDefaultMessage()));
              }else {
                   map.put(fieldName,addValue(new ArrayList<>(),obj.getDefaultMessage()));
              }
            }

            return ResponseEntity.badRequest().body(apiError(null,request));
    }
       private List<String> addValue(List<String> list, String newValue){
              list.add(newValue);
              return list;
       }

    public <E>ApiError<E> apiError(E message, WebRequest request){
           ApiError<E> apiError = new ApiError<>();
            apiError.setStatus(HttpStatus.BAD_REQUEST);


            ExceptionDetails<E> exception = new ExceptionDetails<>();
              exception.setMessage(message);
              exception.setCreateTime(LocalDateTime.now());
              exception.setPath(request.getContextPath());
              exception.setHostName(getHostname());

              apiError.setException(exception);

              return apiError;
    }

      public String getHostname(){
          try {
              return InetAddress.getLocalHost().getHostName();
          } catch (UnknownHostException e) {
             logger.error("Have some Porblems" + e.getMessage());
          }
          return null;

      }
}
