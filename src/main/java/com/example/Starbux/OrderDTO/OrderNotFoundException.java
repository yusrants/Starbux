package com.example.Starbux.OrderDTO;

public class OrderNotFoundException  extends RuntimeException {
   
    OrderNotFoundException(Long id) {
        super("Could not find product " + id);
      }
}
