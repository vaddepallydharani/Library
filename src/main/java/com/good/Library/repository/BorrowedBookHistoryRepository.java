package com.good.Library.repository;

import com.good.Library.entity.BorrowedBookHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BorrowedBookHistoryRepository extends JpaRepository<BorrowedBookHistory, Integer> {
    BorrowedBookHistory findByBookId(Integer bookId);



    @Query("SELECT bh FROM BorrowedBookHistory bh " +
            "WHERE (bh.customerName = :customerName OR bh.customerId = :customerId) " +
            "AND (bh.returned = :returned OR :returned IS NULL)")
    List<BorrowedBookHistory> findByCustomerAndReturned(
            @Param("customerName") String customerName,
            @Param("customerId") Integer customerId,
            @Param("returned") String returned);


}
