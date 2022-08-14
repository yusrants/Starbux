package com.example.Starbux.Customer;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.Starbux.OrderDTO.*;


@Entity
public class Customer {
    private @Id @GeneratedValue Long id;
    private String email;
    @ElementCollection
    private List<OrderDTO> orders;
    private int totalOrders;

    Customer() {
      this.orders = new ArrayList<OrderDTO>();
    }
  
    public Customer(String email) {
  
      this.email = email;
      this.orders = new ArrayList<OrderDTO>();
    }
  
    public Long getId() {
      return this.id;
    }
  
  
    public List<OrderDTO> getOrders() {
      return this.orders;
    }
 
    public String getEmail() {
      return this.email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public void setId(Long id) {
      this.id = id;
    }
  
    public void setOrders(List<OrderDTO> orders) {
      this.orders = orders;
    }

    public void addOrder (OrderDTO order) {
        this.orders.add(order);
    }

    public int getTotalOrders(){
      this.totalOrders = this.orders.size();
      return this.totalOrders;
    }

    @Override
    public boolean equals(Object o) {
  
      if (this == o)
        return true;
      if (!(o instanceof Customer))
        return false;
      Customer Customer = (Customer) o;
      return Objects.equals(this.id, Customer.id) 
          && Objects.equals(this.orders, Customer.orders);
    }
  
    @Override
    public String toString() {
      return "\nCustomer Report:" + "\n" + 
      "Customer email/username: " + this.email + "\n" + 
        "Total no. of orders placed: " + this.getTotalOrders() + "\n";
    }
}
