package com.good.Library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Entity
@Data
@Validated
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer userId;


    @NotNull
    @Size(min = 10, max = 12)
    @Pattern(regexp = "^\\d{10}$")
    public String phoneNumber;

    @NotNull
    @Size(min = 5, max = 200)
    public String address;
}
