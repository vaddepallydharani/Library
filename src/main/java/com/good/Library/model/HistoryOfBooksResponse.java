package com.good.Library.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class HistoryOfBooksResponse {




    private Integer customerId;

    private String customerName;

    private Integer bookId;

    private String bookName;

    private LocalDate borrowedDate;

    private String returnDate;

    public String status;


}
