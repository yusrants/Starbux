package com.example.Starbux.Customer;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository) {
      this.repository = repository;
    }

    @GetMapping("/admin/customers")
    List<Customer> all() {
      return repository.findAll();
    }
  
    @PostMapping("/admin/customers")
    Customer newCustomer(@RequestBody Customer newCustomer) {
      return repository.save(newCustomer);
    }
  
    // Single item
    @GetMapping("/admin/customers/{id}")
    Customer one(@PathVariable Long id) {
      
      return repository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(id));
    }
  
    @PutMapping("/admin/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
      
      return repository.findById(id)
        .map(Customer -> {
          Customer.setOrders(newCustomer.getOrders());
          return repository.save(Customer);
        })
        .orElseGet(() -> {
          newCustomer.setId(id);
          return repository.save(newCustomer);
        });
    }
  
    @DeleteMapping("/admin/customers/{id}")
    void deleteCustomer(@PathVariable Long id) {
      repository.deleteById(id);
    }
}
