package com.example.Starbux.OrderDTO;

import java.util.Objects;
import java.util.List;

import javax.persistence.*;

import com.example.Starbux.OrderDTO.OrderItem.OrderItem;
import com.example.Starbux.Utils.Utils;

@Entity
public class OrderDTO {
    private @Id @GeneratedValue Long id;

    @ElementCollection 
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> items;
    private String customerEmail;
    private float total;
    private float discountedTotal;

    OrderDTO() {}
  
    OrderDTO(List<OrderItem> items, String customerEmail) {
  
      this.items = items;
      this.customerEmail = Utils.convertString(customerEmail);
    }
  
    public Long getId() {
      return this.id;
    }
  
    public List<OrderItem> getItems() {
      return this.items;
    }
  
   public float getTotal() {
        return this.total;
    }

    public float getDiscountedTotal() {
      return this.discountedTotal;
    }

    public void setTotal(float amount) {
      this.total = amount;
  }

  public void setDiscountedTotal(float amount) {
    this.discountedTotal= amount;
  } 

    public String getCustomer() {
      return this.customerEmail;
    }

    public void setId(Long id) {
      this.id = id;
    }
  
    public void setCustomer(String customerEmail) {
      this.customerEmail = Utils.convertString(customerEmail);
    }
  
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
  
      if (this == o)
        return true;
      if (!(o instanceof OrderDTO))
        return false;
      OrderDTO Order = (OrderDTO) o;
      return Objects.equals(this.id, Order.id) && Objects.equals(this.items, Order.items) && 
      Objects.equals(this.customerEmail, Order.customerEmail);
    }
  
    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.items, this.customerEmail);
    }
  
    @Override
    public String toString() {
      return "Order{" + "id=" + this.id + ", customerEmail='" + this.customerEmail + '\'' + '}';
    }
}
