package com.good.Library.service;

import com.good.Library.exception.BookNameExistException;
import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    public BookDetailsEntity addNewBook(BookDetailsEntity bookDetails) throws BookNameExistException {

        Optional<BookDetailsEntity> existingBook=bookRepository.findByBookName(bookDetails.getBookName());
       /* boolean falg = true;

        if (falg){

        }*/

        if (existingBook == null) {
            return bookRepository.save(bookDetails);
        }

        else{
            throw new BookNameExistException("Book is already exist");
            }
    }


    @Override
    public List<BookDetailsEntity> getAllBooks() {
        List<BookDetailsEntity> listOfBooks=bookRepository.findAll();
        if(listOfBooks == null) {
            throw new ResponseStatusException(NOT_FOUND, "No Books found in Library");
        }
        return listOfBooks;
    }

    public Optional<BookDetailsEntity> getBookByName(String bookName){
        Optional<BookDetailsEntity> book = bookRepository.findByBookName(bookName);
        if(book == null )
        {
            throw new ResponseStatusException(NOT_FOUND,"There is no book with that name. Please enter the correct name");
        }
            return book;
    }


}
