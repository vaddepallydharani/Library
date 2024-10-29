package com.good.Library.repository;

import com.good.Library.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer> {
    Optional<BorrowedBook> findByBookId(Integer bookId);

    List<BorrowedBook> findByCustomerId(Integer customerId);



}
