package com.good.Library.service;

import com.good.Library.BookNameExistException;
import com.good.Library.entity.BookDetailsEntity;
import java.util.List;
import java.util.Optional;


public interface IBookService {
    public BookDetailsEntity addNewBook(BookDetailsEntity bookDetails) throws BookNameExistException;

    public Optional<BookDetailsEntity> getBookByName(String bookName);

    public List<BookDetailsEntity> getAllBooks();
}
