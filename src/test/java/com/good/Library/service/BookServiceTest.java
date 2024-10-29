package com.good.Library.service;

import com.good.Library.exception.BookNameExistException;
import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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

        doReturn(null).when(bookRepository).findByBookName(bookDetails.getBookName());

        when(bookRepository.save(bookDetails)).thenReturn(bookDetails);
        BookDetailsEntity expectedBookDetails= bookService.addNewBook(bookDetails);

        assertEquals(bookDetails,expectedBookDetails);
    }

    @Test
    public void addNewBook_bookExist() throws BookNameExistException{
        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Optional<BookDetailsEntity> optionalBookDetailsEntity = Optional.of(bookDetails);

        doReturn(Optional.of(optionalBookDetailsEntity)).when(bookRepository).findByBookName(Mockito.matches("Ivy the very determined dog"));

        when(bookRepository.save(bookDetails)).thenReturn(bookDetails);

        BookNameExistException bookNameExistException = assertThrows(BookNameExistException.class,
				() -> bookService.addNewBook(bookDetails));

        assertEquals("Book is already exist",bookNameExistException.getMessage());

    }

    @Test
    public void testGetAllBooks_Success() {
        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(bookDetails));
        List<BookDetailsEntity> expectedBooksList= bookService.getAllBooks();

        assertEquals(List.of(bookDetails),expectedBooksList);

    }

    @Test
    public void testGetAllBooks_Null() throws ResponseStatusException {
        Mockito.when(bookRepository.findAll()).thenReturn(null).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> bookService.getAllBooks());

       assertEquals(HttpStatus.NOT_FOUND,responseStatusException.getStatusCode());
       assertEquals("No Books found in Library",responseStatusException.getReason());
    }

    @Test
    public void testGetBookByName_success() {
        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Optional<BookDetailsEntity> optionalBookDetailsEntity = Optional.of(bookDetails);

        Mockito.when(bookRepository.findByBookName(bookDetails.getBookName())).thenReturn(optionalBookDetailsEntity);

        Optional<BookDetailsEntity> expectedBook = bookService.getBookByName(bookDetails.getBookName());

        assertEquals(optionalBookDetailsEntity,expectedBook);
    }

    @Test
    public void testGetBookByName_Null() throws ResponseStatusException {
        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Mockito.when(bookRepository.findByBookName(bookDetails.getBookName())).thenReturn(null).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> bookService.getBookByName(bookDetails.getBookName()));

        assertEquals(HttpStatus.NOT_FOUND,responseStatusException.getStatusCode());
        assertEquals("We don't have a book by the id you gave. Please try again.",responseStatusException.getReason());
    }


}
