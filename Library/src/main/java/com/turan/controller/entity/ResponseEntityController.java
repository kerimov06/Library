package com.turan.controller.entity;

import com.turan.entity.ResponseEntity;

public class ResponseEntityController {


    public <T>ResponseEntity<T> ok(T data){
          return ResponseEntity.ok(data);
    }

    public <T>ResponseEntity<T> error(String errorMessage){
          return ResponseEntity.error(errorMessage);
    }
}
