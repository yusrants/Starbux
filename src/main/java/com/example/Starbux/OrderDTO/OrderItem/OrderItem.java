package com.example.Starbux.OrderDTO.OrderItem;

import java.util.List;

import javax.persistence.*;

import com.example.Starbux.Utils.Utils;

@Entity
public class OrderItem {
    private @Id @GeneratedValue
    Long id;
    private String message;
    private Long drinkId;
    private String drinkName;
    @ElementCollection
    private List<String> toppingNames;
    private int quantity = 1;

    OrderItem() {
      this.message = "";
    }

    OrderItem(String drinkName, List<String> toppingNames, int quantity) {
  
      this.quantity = quantity;
      this.drinkName = Utils.convertString(drinkName);
      this.toppingNames = Utils.convertStringArray(toppingNames);
      this.message = "";
    }

    public Long getId() {
      return this.id;
    }

    public Long getDrinkId() {
      return this.drinkId;
    }
   
    public String getDrinkName() {
      return  Utils.convertString(this.drinkName);
    }

    public List<String> getToppingNames() {
      return Utils.convertStringArray(this.toppingNames);
    }
    
    public void setToppingNames(List<String> toppings){
        this.toppingNames = Utils.convertStringArray(toppings);
    }
    public int getQuantity() {
      return this.quantity;
    }
    public String getMessage() {
      return this.message;
    }
    public void setId(Long id) {
      this.id = id;
    }
  
    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }
    public void setDrinkId(Long id) {
      this.drinkId = id;
    }
    public void addMessage(String msg) {
        this.message += msg;
    }
}
