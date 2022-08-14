package com.example.Starbux.Customer;

public class CustomerNotFoundException  extends RuntimeException {
   
    CustomerNotFoundException(Long id) {
        super("Could not find Customer " + id);
      }
}
