package com.mtab.mtabapp.repository;

import com.mtab.mtabapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAddress(String emailAddress);
}
