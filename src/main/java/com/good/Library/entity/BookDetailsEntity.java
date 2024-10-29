package com.good.Library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="bookDetails")
public class BookDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer bookId;
    public String bookName;
    public String bookAuthor;
    public String bookPlace;
    @NotNull
    @Column(name = "availability", length = 1)
    public char availability;
    @NotNull
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'None'")
    public String borrowedCustomerName;



}
