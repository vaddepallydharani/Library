package com.good.Library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="bookDetails")
public class BookDetailsEntity {

    @Id
    public int bookId;
    public String bookName;
    public String bookAuthor;
    public String bookPlace;


}
