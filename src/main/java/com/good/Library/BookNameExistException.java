package com.good.Library;

public class BookNameExistException extends RuntimeException{

  public BookNameExistException(){

    }

    public BookNameExistException(String message){
      super(message);

    }

}
