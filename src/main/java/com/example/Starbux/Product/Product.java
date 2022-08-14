package com.example.Starbux.Product;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.example.Starbux.Utils.Utils;

@Entity
public class Product {
    private @Id @GeneratedValue Long id;
    private String name;

    private String category = "drink"; // either drink or topping, default: drink
    private float price = 0f; // Default value: 0f
    @ElementCollection
    private List<Long> toppings; //For drinks: Keeps track of the toppings ordered with this product

    public Product() {}
  
    public Product(String productName, String category, float price) {

      this.name = Utils.convertString(productName); 
      this.category = Utils.convertString(category);
      this.price = price;
    }
  
    public Long getId() {
      return this.id;
    }
  
    public String getName() {
      return Utils.convertString(this.name);
    }

    public List<Long> getToppings() {
      return this.toppings;
    }

    public void addTopping( Long id) {
      this.toppings.add(id);
    }

    public String getCategory() {
      return Utils.convertString(this.category);
    }
 
    public float getPrice() {
        return this.price;
    }

    public void setId(Long id) {
      this.id = id;
    }
  
    public void setName(String name) {
      this.name = Utils.convertString(name);
    }
  
    public void setCategory(String category) {
      this.category = Utils.convertString(category);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Long> getMostUsedTopping(){
      if (this.category == "drink") 
          return Utils.getMostCommonElement(this.getToppings());
      else return null;
    }

    @Override
    public boolean equals(Object o) {
  
      if (this == o)
        return true;
      if (!(o instanceof Product))
        return false;
      Product Product = (Product) o;
      return Objects.equals(this.id, Product.id) && Objects.equals(this.name, Product.name)
          && Objects.equals(this.category, Product.category) && Objects.equals(this.price, Product.price);
    }
  
    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.name, this.category, this.price, this.toppings);
    }
  
    @Override
    public String toString() {
      return "{" + "id=" + this.id + ", name='" + this.name + '\'' + ", category='" + this.category + 
      '\''+ ", price='" + this.price + '\'' + '}';
    }
}
