package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastname(@Param("name") String name);
}
