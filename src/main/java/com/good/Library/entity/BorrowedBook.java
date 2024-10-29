package com.good.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

   /* @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "customer_book_id")
    private List<BookDetailsEntity> book;*/

    @NotNull
    @Column(unique=true)
    private Integer customerId;

    private String customerName;

    private Integer bookId;

    private String bookName;

    private LocalDate borrowedDate;

}
