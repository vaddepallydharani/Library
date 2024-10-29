package com.good.Library.controller;

import com.good.Library.exception.BookNameExistException;
import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/new-book")
    public BookDetailsEntity addNewBook(@RequestBody BookDetailsEntity bookDetails) throws BookNameExistException {
        return bookService.addNewBook(bookDetails);
    }

    @GetMapping("/list-of-books")
    public List<BookDetailsEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/getByBookName")
    public Optional<BookDetailsEntity> getBookByName(@RequestParam String bookName){
        return bookService.getBookByName(bookName);
    }
}