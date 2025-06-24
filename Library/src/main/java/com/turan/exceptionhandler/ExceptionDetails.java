package com.turan.exceptionhandler;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ExceptionDetails<E> {


     private String path;
     private E message;
     private LocalDateTime createTime;
     private String hostName;


}
