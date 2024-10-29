package com.good.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @NotNull
    @Column(unique=true)
    @Size(min = 3, max = 50)
    public String userName;

    @NotNull
    @Past
    public LocalDate UserDOB;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    public UserDetails userDetails;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
