package com.good.Library.controller;

import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.entity.BorrowedBook;
import com.good.Library.entity.User;
import com.good.Library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@Valid @RequestBody User user) {
            User registeredUser = userService.registration(user);
            return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/all-books")
    public List<BookDetailsEntity> getBooks(){
        return userService.getAllBooks();
    }

    @GetMapping("/book-by-id/{bookId}")
    public Optional<BookDetailsEntity> bookDetailsById(@PathVariable int bookId){
        return userService.bookDetailsById(bookId);
    }

    @PostMapping("/add-book")
    public ResponseEntity<BookDetailsEntity> addBook(@RequestBody BookDetailsEntity bookDetailsEntity){
        BookDetailsEntity bookDetails=userService.addingNewBook(bookDetailsEntity);
        return ResponseEntity.ok(bookDetails);
    }

    @PostMapping("/giving-books-to-customer")
    public ResponseEntity<BorrowedBook> addingBooksToCustomer(@RequestBody BorrowedBook borrowedBook){
      BorrowedBook book=userService.addingBooksToCustomer(borrowedBook);
      return ResponseEntity.ok(book);
    }


    @GetMapping("/books-by-customer")
    public List<BorrowedBook> BorrowedBooksByCustomer(@RequestParam Integer customerId){
        return userService.findAllBorrowedBooksByCustomer(customerId);
    }

    @DeleteMapping("/checkout-books/{id}")
    public ResponseEntity<String> checkoutBorrowedBooks(@PathVariable Integer id){
        userService.deleteBookFromCustomer(id);
        return new ResponseEntity<>("Checked out book Successfully", HttpStatus.OK);
    }


}
