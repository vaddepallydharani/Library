package com.good.Library.repository;

import com.good.Library.entity.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerDetailsEntity,Integer> {
}
