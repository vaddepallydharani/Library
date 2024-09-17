package com.good.Library.repository;

import com.good.Library.entity.BookDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookDetailsEntity, Integer> {
    Optional<BookDetailsEntity> findByBookName(String bookName);


}
