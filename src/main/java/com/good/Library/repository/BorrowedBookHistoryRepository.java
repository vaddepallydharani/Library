package com.good.Library.repository;

import com.good.Library.entity.BorrowedBookHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedBookHistoryRepository extends JpaRepository<BorrowedBookHistory, Integer> {
    BorrowedBookHistory findByBookId(Integer bookId);
}
