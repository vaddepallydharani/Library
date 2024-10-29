package com.good.Library.service;

import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.exception.UserAlreadyExistException;
import com.good.Library.entity.User;
import com.good.Library.entity.UserDetails;
import com.good.Library.entity.UserRole;
import com.good.Library.repository.BookRepository;
import com.good.Library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BookRepository bookRepository;

    @Test
    public void registrationTest_Success() throws UserAlreadyExistException {

        User user = new User();
        user.setUserName("junnu26280");
        user.setUserDOB(LocalDate.parse("2017-06-17"));
        user.setUserRole(UserRole.ADMIN);

        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("Hyderabad");
        userDetails.setPhoneNumber("9999999999");

        user.setUserDetails(userDetails);

        doReturn(Optional.empty()).when(userRepository).findByUserName(user.getUserName());

        when(userRepository.save(user)).thenReturn(user);
        User expecteduserdetails= userService.registration(user);

        assertEquals(user,expecteduserdetails);
    }

    @Test
    public void registrationTest_UserExists() throws UserAlreadyExistException{
        User user = new User();
        user.setUserName("junnu26280");
        user.setUserDOB(LocalDate.parse("2017-06-17"));
        user.setUserRole(UserRole.ADMIN);

        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("Hyderabad");
        userDetails.setPhoneNumber("9999999999");

        user.setUserDetails(userDetails);

        doReturn(Optional.of(user)).when(userRepository).findByUserName(Mockito.matches("junnu26280"));

        when(userRepository.save(user)).thenReturn(user);
        UserAlreadyExistException userAlreadyExistException = assertThrows(UserAlreadyExistException.class,
                () -> userService.registration(user));

        assertEquals("User is already registered",userAlreadyExistException.getMessage());

    }

    @Test
    public void testGetAllBooks_Success(){

        BookDetailsEntity bookDetails = new BookDetailsEntity();

        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");

        Mockito.when(bookRepository.findAll()).thenReturn(List.of(bookDetails));
        List<BookDetailsEntity> expectedBooksList= userService.getAllBooks();

        assertEquals(List.of(bookDetails),expectedBooksList);
    }
    @Test
    public void testGetAllBooks_NoBooks() throws ResponseStatusException {
         Mockito.when(bookRepository.findAll()).thenReturn(null).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> userService.getAllBooks());

        assertEquals(HttpStatus.NOT_FOUND,responseStatusException.getStatusCode());
        assertEquals("No Books found in Library",responseStatusException.getReason());
    }

    @Test
    public void testGetBookById_success() {
        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Optional<BookDetailsEntity> optionalBookDetailsEntity = Optional.of(bookDetails);

        Mockito.when(bookRepository.findById(bookDetails.getBookId())).thenReturn(optionalBookDetailsEntity);

        Optional<BookDetailsEntity> expectedBook = userService.bookDetailsById(bookDetails.getBookId());

        assertEquals(optionalBookDetailsEntity,expectedBook);
    }

    @Test
    public void testGetBookById_Null() throws ResponseStatusException {
        BookDetailsEntity bookDetails = new BookDetailsEntity();
        bookDetails.setBookId(1);
        bookDetails.setBookName("Ivy the very determined dog");
        bookDetails.setBookAuthor("Harrington");
        bookDetails.setBookPlace("A1");
        Mockito.when(bookRepository.findById(bookDetails.getBookId())).thenReturn(Optional.empty()).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> userService.bookDetailsById(bookDetails.getBookId()));

        assertEquals(HttpStatus.NOT_FOUND,responseStatusException.getStatusCode());
        assertEquals("We don't have a book by the id you gave. Please try again.",responseStatusException.getReason());
    }

}
