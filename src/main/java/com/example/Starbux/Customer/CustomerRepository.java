package com.example.Starbux.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer getCustomerByEmail(String email);
}
