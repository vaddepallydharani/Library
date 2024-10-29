package com.good.Library.service;

import com.good.Library.exception.BookNameExistException;
import com.good.Library.entity.BookDetailsEntity;
import java.util.List;
import java.util.Optional;

public interface BookService {
    public BookDetailsEntity addNewBook(BookDetailsEntity bookDetails) throws BookNameExistException;

    public Optional<BookDetailsEntity> getBookByName(String bookName);

    public List<BookDetailsEntity> getAllBooks();
}
