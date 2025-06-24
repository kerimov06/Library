package com.turan.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public enum  MessageType {

      NO_RECORD_EXIST("404" , "Sorry , Your data haven't in data base"),
      GLOBAL_EXCEPTION("202", "Sorry! Some have problem try again");



     private String code;
     private String message;


      MessageType(String code,String message){
            this.code = code;
            this.message = message;
      }

    }


