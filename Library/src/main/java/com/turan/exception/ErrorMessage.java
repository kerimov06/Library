package com.turan.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    @Autowired
    private MessageType messageType;


     public String generateException(){

          StringBuilder builder = new StringBuilder();

           builder.append(messageType.getMessage());

           return builder.toString();
     }


}
