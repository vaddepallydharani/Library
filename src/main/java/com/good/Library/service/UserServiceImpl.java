package com.good.Library.service;

//import com.good.Library.BorrowedBookMapper;

import com.good.Library.entity.*;
import com.good.Library.exception.BookNameExistException;
import com.good.Library.exception.UserAlreadyExistException;
import com.good.Library.model.HistoryOfBooksResponse;
import com.good.Library.repository.BookRepository;
import com.good.Library.repository.BorrowedBookHistoryRepository;
import com.good.Library.repository.BorrowedBookRepository;
import com.good.Library.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    @Autowired
    BorrowedBookHistoryRepository borrowedBookHistoryRepository;


    public User registration(User user) {
        Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser.isEmpty()) {
            return userRepository.save(user);
        } else {
            throw new UserAlreadyExistException("User is already registered");
        }
    }

    @Override
    public List<BookDetailsEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<BookDetailsEntity> bookDetailsById(Integer bookId) {
        Optional<BookDetailsEntity> book = bookRepository.findById(bookId);
        if (book.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "We don't have a book by the id you gave. Please try again.");
        return book;
    }

    @Override
    public BookDetailsEntity addingNewBook(BookDetailsEntity bookDetailsEntity) {
        Optional<BookDetailsEntity> existingBook = bookRepository.findByBookName(bookDetailsEntity.getBookName());
        if (existingBook.isEmpty())
            return bookRepository.save(bookDetailsEntity);
        else
            throw new BookNameExistException("Book is already present");
    }

    @Override
    public List<BorrowedBook> findAllBorrowedBooksByCustomer(Integer customerId) {
        return borrowedBookRepository.findByCustomerId(customerId);
    }

    @Override
    public BorrowedBook addingBooksToCustomer(BorrowedBook borrowedBook) {
        BorrowedBook savedBorrowBook;

        Optional<BorrowedBook> borrowedBookByBookId = borrowedBookRepository.findByBookId(borrowedBook.getBookId());
        if (borrowedBookByBookId.isEmpty()) {
            borrowedBook.setBorrowedDate(LocalDate.now());
            savedBorrowBook = borrowedBookRepository.save(borrowedBook);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is assigned to someone");
        }

        saveBorrowedBookHistory(savedBorrowBook);


        Optional<BookDetailsEntity> bookDetailsEntity;
        bookDetailsEntity = bookRepository.findById(savedBorrowBook.getBookId());
        if (bookDetailsEntity.isPresent()) {
            bookDetailsEntity.get().setAvailability('N');
            bookDetailsEntity.get().setBorrowedCustomerName(savedBorrowBook.getCustomerName());
            bookRepository.save(bookDetailsEntity.get());
        }
        return savedBorrowBook;
    }

    private void saveBorrowedBookHistory(BorrowedBook savedBorrowBook) {
        BorrowedBookHistory borrowedBookHistory = new BorrowedBookHistory();
        borrowedBookHistory.setBookId(savedBorrowBook.getBookId());
        borrowedBookHistory.setBookName(savedBorrowBook.getBookName());
        borrowedBookHistory.setCustomerId(savedBorrowBook.getCustomerId());
        borrowedBookHistory.setCustomerName(savedBorrowBook.getCustomerName());
        borrowedBookHistory.setBorrowedDate(LocalDate.now());
        borrowedBookHistory.setReturnDate(null);
        borrowedBookHistory.setReturned("N");
        borrowedBookHistoryRepository.save(borrowedBookHistory);
    }

    @Override
    public void deleteBookFromCustomer(Integer bookId) {

        Optional<BorrowedBook> getBorrowedBookById = borrowedBookRepository.findByBookId(bookId);
        if (getBorrowedBookById.isPresent()) {
            BorrowedBook borrowedBook = getBorrowedBookById.get();
            borrowedBookRepository.delete(borrowedBook);

            BorrowedBookHistory borrowedBookHistory = borrowedBookHistoryRepository.findByBookId(bookId);
            borrowedBookHistory.setReturnDate(LocalDate.now());
            borrowedBookHistory.setReturned("Y");
            borrowedBookHistoryRepository.save(borrowedBookHistory);


            Optional<BookDetailsEntity> bookDetails = bookRepository.findById(borrowedBook.getBookId());
            if (bookDetails.isPresent()) {
                BookDetailsEntity bookDetailsEntity = bookDetails.get();
                bookDetailsEntity.setAvailability('Y');
                bookDetailsEntity.setBorrowedCustomerName("None");
                bookRepository.save(bookDetailsEntity);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please enter valid bookId");
        }
    }

    @Override
    public List<HistoryOfBooksResponse> historyOfBorrowedAndCheckoutBooks(Integer customerId, String customerName, String status) {
        String finalStatus = null;
        if (StringUtils.isNotEmpty(status)) {
            if(status.equalsIgnoreCase("returned")){
                finalStatus = "Y";
            } else if(status.equalsIgnoreCase("borrowed")){
                finalStatus = "N";
            }

        }

        List<BorrowedBookHistory> bookHistoryList;

        bookHistoryList = borrowedBookHistoryRepository.findByCustomerAndReturned(customerName, customerId, finalStatus);

        List<HistoryOfBooksResponse> list = new ArrayList<>();

        for (BorrowedBookHistory bookHistory : bookHistoryList) {

            HistoryOfBooksResponse historyOfBooksResponse = new HistoryOfBooksResponse();
            historyOfBooksResponse.setCustomerId(bookHistory.getCustomerId());
            historyOfBooksResponse.setCustomerName(bookHistory.getCustomerName());
            historyOfBooksResponse.setBookId(bookHistory.getBookId());
            historyOfBooksResponse.setBookName(bookHistory.getBookName());
            historyOfBooksResponse.setBorrowedDate(bookHistory.getBorrowedDate());
            if (bookHistory.getReturned().equalsIgnoreCase("Y")) {
                historyOfBooksResponse.setReturnDate(String.valueOf(bookHistory.getReturnDate()));
            } else {
                historyOfBooksResponse.setReturnDate("no return date");
            }
            historyOfBooksResponse.setStatus(bookHistory.getReturned().equals("Y") ? "returned" : "not returned");
            list.add(historyOfBooksResponse);
        }
        return list;
    }

}

