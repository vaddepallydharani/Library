package com.good.Library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryOfBooksResponse {

    private Integer customerId;

    private String customerName;

    private Integer bookId;

    private String bookName;

    private LocalDate borrowedDate;

    private String returnDate;

    public String status;


}
