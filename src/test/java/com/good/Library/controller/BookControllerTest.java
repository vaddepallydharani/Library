/*
package com.good.Library.controller;


import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookControllerTest {


    @Autowired
    private BookController bookController;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    public void addNewBook_success()  {

        BookDetailsEntity newBook = new BookDetailsEntity();
        newBook.setBookId(1);
        newBook.setBookName("Ivy the very determined dog");
        newBook.setBookAuthor("Harrington");
        newBook.setBookPlace("A1");

        Mockito.when(bookService.addNewBook(newBook)).thenReturn(newBook);
        BookDetailsEntity savedBook = bookController.addNewBook(newBook);

        assertEquals(newBook, savedBook);
    }

}
*/
