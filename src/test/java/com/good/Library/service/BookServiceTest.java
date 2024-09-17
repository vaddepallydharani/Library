package com.good.Library.service;

import com.good.Library.BookNameExistException;
import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    BookRepository bookRepository;

    @Test
    public void addNewBook_success() throws BookNameExistException {

        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Optional<BookDetailsEntity> optionalBookDetailsEntity = Optional.of(bookDetails);

        //doReturn(Optional.of(optionalBookDetailsEntity)).when(bookRepository).findByBookName(Mockito.anyString());
        doReturn(null).when(bookRepository).findByBookName(Mockito.anyString());

        when(bookRepository.save(bookDetails)).thenReturn(bookDetails);
        BookDetailsEntity expectedBookDetails= bookService.addNewBook(bookDetails);

        assertEquals(bookDetails,expectedBookDetails);
    }

    /*@Test
    public void addNewBook_existingBook() throws Exception {

        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Optional<BookDetailsEntity> optionalBookDetailsEntity = Optional.of(bookDetails);

        //doReturn(Optional.of(optionalBookDetailsEntity)).when(bookRepository).findByBookName(Mockito.anyString());
        doReturn(Optional.of(optionalBookDetailsEntity)).when(bookRepository).findByBookName(Mockito.anyString());
        when(bookRepository.save(bookDetails)).thenThrow(new RuntimeException("Book is already exis"));
       // when(bookRepository.save(bookDetails)).thenReturn(bookDetails);
        bookService.addNewBook(bookDetails);

        assertTrue(true);
       // assertEquals( RuntimeException,expectedBookDetails);
    }
*/


}
