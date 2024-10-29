package com.good.Library.service;

import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.entity.BorrowedBook;
import com.good.Library.exception.BookNameExistException;
import com.good.Library.exception.UserAlreadyExistException;
import com.good.Library.entity.User;
import com.good.Library.entity.UserRole;
import com.good.Library.repository.BookRepository;
import com.good.Library.repository.BorrowedBookRepository;
import com.good.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    public boolean isAdmin(User user){
        return user.getUserRole() == UserRole.ADMIN;
    }

    public User registration(User user) {
        Optional<User> existingUser=userRepository.findByUserName(user.getUserName());
        if(!existingUser.isPresent()) {
            return userRepository.save(user);
        }
        else{
          throw  new UserAlreadyExistException("User is already registered");
        }
    }

    @Override
    public List<BookDetailsEntity> getAllBooks() {
        List<BookDetailsEntity> listOfBooks=bookRepository.findAll();
        if(listOfBooks == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Books found in Library");
        }
        return listOfBooks;
    }

   @Override
    public Optional<BookDetailsEntity> bookDetailsById(Integer bookId) {
        Optional<BookDetailsEntity> book= bookRepository.findById(bookId);
        if(!book.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"We don't have a book by the id you gave. Please try again.");
        return book;
    }

    @Override
    public BookDetailsEntity addingNewBook(BookDetailsEntity bookDetailsEntity) {
      Optional<BookDetailsEntity> existingBook= bookRepository.findByBookName(bookDetailsEntity.getBookName());
      if(!existingBook.isPresent())
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
        BorrowedBook savedBorrowBook = null;
        //First we need to get the borrowed book details based on book_Id
      Optional<BorrowedBook> borrowedBookByBookId = borrowedBookRepository.findByBookId(borrowedBook.getBookId());
      if(!borrowedBookByBookId.isPresent()){
          savedBorrowBook = borrowedBookRepository.save(borrowedBook);
      }else{
          //TODO
      }

        //this logic is to make book availability N in booksEntity.
        Optional<BookDetailsEntity> bookDetailsEntity = null;
      if(savedBorrowBook!=null) {
          bookDetailsEntity = bookRepository.findById(savedBorrowBook.getBookId());
          if (bookDetailsEntity.isPresent()) {
              bookDetailsEntity.get().setAvailability('N');
              bookDetailsEntity.get().setBorrowedCustomerName(savedBorrowBook.getCustomerName());
              bookRepository.save(bookDetailsEntity.get());
          }
      }
        return savedBorrowBook;
    }

    @Override
    public void deleteBookFromCustomer(Integer id) {

        Optional<BorrowedBook> getBorrowedBookById  = borrowedBookRepository.findByBookId(id);
        if(getBorrowedBookById.isPresent()){
            BorrowedBook borrowedBook = getBorrowedBookById.get();
            borrowedBookRepository.delete(borrowedBook);

            Optional<BookDetailsEntity> bookDetails= bookRepository.findById(borrowedBook.getBookId());
            if (bookDetails.isPresent()) {
                BookDetailsEntity bookDetailsEntity = bookDetails.get();
                bookDetailsEntity.setAvailability('Y');
                bookDetailsEntity.setBorrowedCustomerName("None");
                bookRepository.save(bookDetailsEntity);
            }
        }else{
            //ToDo

        }


    }


}
