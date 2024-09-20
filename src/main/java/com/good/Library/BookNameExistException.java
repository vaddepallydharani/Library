package com.good.Library;

public class BookNameExistException extends RuntimeException{

    public BookNameExistException(String message){
      super(message);
    }

}
