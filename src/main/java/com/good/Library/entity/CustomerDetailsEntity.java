package com.good.Library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="customerDetails")
public class CustomerDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public  int customerId;
    public String customerName;
    public String customerAddress;
}
