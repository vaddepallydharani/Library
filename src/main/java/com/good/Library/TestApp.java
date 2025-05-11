package com.good.Library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.good.Library.entity.BookDetailsEntity;

public class TestApp {

    public static void main(String[] args) throws JsonProcessingException {
        String str = "abc\\000u\\";

        System.out.println("final String : "+str.replace("\\000u\\",""));

        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(123);
        bookDetails.setBookName("HHH");
        bookDetails.setBookPlace("B");

        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(bookDetails);
        System.out.println("payload is :: "+payload);
    }
}
