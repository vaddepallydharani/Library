package com.good.Library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="customerDetails")
public class CustomerDetailsEntity {

    @Id
    public  int customerId;
    public String customerName;
    public String customerAddress;
}
