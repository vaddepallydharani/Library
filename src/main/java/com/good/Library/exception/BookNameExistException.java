package com.good.Library.exception;

public class BookNameExistException extends RuntimeException{

    public BookNameExistException(String message){
      super(message);
    }

}
