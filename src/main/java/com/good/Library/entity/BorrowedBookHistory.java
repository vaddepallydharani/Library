package com.good.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class BorrowedBookHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer historyId;

    @NotNull
    @Column(unique=true)
    private Integer customerId;

    private String customerName;

    private Integer bookId;

    private String bookName;

    private LocalDate borrowedDate;

    private LocalDate returnDate;
    public BorrowedBookHistory() {
        this.returnDate = LocalDate.now();
    }

    @NotNull
    @Column(name = "returned", length = 1)
    public char returned;



}
