package com.good.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer historyId;

    private Integer customerId;

    private String customerName;

    private Integer bookId;

    private String bookName;

    private LocalDate borrowedDate;

    private LocalDate returnDate;

    @NotNull
    @Column(name = "returned")
    public String returned;



}
