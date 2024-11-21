package com.good.Library.service;

import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.entity.BorrowedBook;
import com.good.Library.entity.User;
import com.good.Library.model.HistoryOfBooksResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {

     User registration(User user);

    List<BookDetailsEntity> getAllBooks();

    Optional<BookDetailsEntity> bookDetailsById(Integer bookId);

    BookDetailsEntity addingNewBook(BookDetailsEntity bookDetailsEntity);

    List<BorrowedBook> findAllBorrowedBooksByCustomer(Integer id);

    BorrowedBook addingBooksToCustomer(BorrowedBook borrowedBook);

    void deleteBookFromCustomer(Integer id);

    List<HistoryOfBooksResponse> historyOfBorrowedAndCheckoutBooks(Integer customerId, String customerName, String status);
}
