package com.example.Starbux.OrderDTO;

import java.util.ArrayList;
import java.util.List;

import com.example.Starbux.OrderDTO.OrderItem.OrderItem;
import com.example.Starbux.Product.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderService {
    
    private ProductRepository productRepo;
    private ProductService productService;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(ProductRepository productRepo) {
        this.productRepo = productRepo;
        this.productService = new ProductService(productRepo);
    }

    public boolean isDrink (Product product) {
        if(product.getCategory() == "drink") 
            return true;
        else return false;
    }

    public boolean isTopping (Product product) {
        if(product.getCategory() == "topping") 
            return true;
        else return false;
    }

    public boolean verifyOrder(OrderDTO order) {
        List<OrderItem> items = order.getItems();

        if (items == null || order.getCustomer() == null) 
            return false;
        else {
            for (OrderItem item: items) {
                if(item.getDrinkName() == null) 
                return false;
            }
        }

        return true;
    }

    public float getProductPrice(String name) {
        float price = 0f;
        String productName = name;
        Product product = productRepo.findByName(productName);

        if (product != null) {
            price =  product.getPrice();
        }

        return price;
    }

    public float getDiscountedPrice(float amount) {
        return (float) (amount - (amount * 0.25));
    }

    // To keep track of most used toppings for each drink:
    public void addDrinkTopping(List<OrderItem> items) {
        for (OrderItem item : items) {
            String drinkName = item.getDrinkName();
            List<String> toppings = item.getToppingNames();
            if (toppings == null) return;
            Product product = productRepo.findByName(drinkName);
            if (product != null) {
                for (String topping : toppings) {
                    String toppingName = topping;
                    Long toppingId = productService.getProductIdfromName(toppingName);

                    for (int i =0; i < item.getQuantity(); i++) {
                        log.info("Adding topping " + topping + " to drink " + product.getName());
                        product.addTopping(toppingId);
                    }
                }
            }
        }
    }

    public void calculateTotal(OrderDTO order) {

        List<OrderItem> items = order.getItems();
        log.info("Calculating cart total for the order " + order.getId());
        addDrinkTopping(items);
        float totalCartAmount = 0f;
        float discountedTotal = 0f;
        float mininumPrice = Float.MAX_VALUE;
        int drinks = 0;

        for (OrderItem item : items) {
                float totalItemAmount = 0f;
                // Extract id(s) for all the products (drink + toppings) in the ordered item:
                String drinkName = item.getDrinkName();
                int quantity = item.getQuantity();
                List<String> toppings = item.getToppingNames();
                Product drink = productRepo.findByName(drinkName);
                if (drink != null && isDrink(drink)) {

                    item.setDrinkId(productService.getProductIdfromName(drinkName));
                    item.addMessage("Successfully added drink! ");
                    totalItemAmount += getProductPrice(drinkName);
                    drinks += quantity;
                }
                
                else {
                    toppings = null;
                    item.setToppingNames(null); 
                    item.addMessage("Sorry, the item you added: " + drinkName + " is not a drink item at Starbux! ");                        
                    item.addMessage("Toppings can't be bought without any drink! ");
                }

                if(toppings != null) {
                List<String> notAvailableToppings = new ArrayList<String>();
                for (String topping : toppings) {

                    Product toppingProduct = productRepo.findByName(topping);

                    if (toppingProduct != null && isTopping(toppingProduct)) {
                        if (getProductPrice(topping) > 0f) {
                            totalItemAmount += getProductPrice(topping);
                        }
                        else {
                            notAvailableToppings.add(topping);
                        }
                    }
                    else {
                        item.addMessage("Sorry, the item you added: " + topping + " is not a topping item at Starbux! ");
                    }
                }
                if (notAvailableToppings.size() > 0) {
                    item.addMessage("Sorry, the toppings: " + notAvailableToppings + " are not available. ");
                }
            }

                // Keeping track of the drink with the lowest price (incl. toppings)
                if (totalItemAmount < mininumPrice) {
                    mininumPrice = totalItemAmount;
                }
                totalItemAmount = totalItemAmount * item.getQuantity();
                log.info("The total item amount is " + totalItemAmount);
                totalCartAmount += totalItemAmount;
        }

        log.info("The total cart amount is " + totalCartAmount);
        order.setTotal(totalCartAmount);

        if (drinks >=3 && totalCartAmount > 12) {
            log.info("Cart has more than 3 items & the total is more than 12 euros!");
            discountedTotal = Math.min((totalCartAmount - mininumPrice), getDiscountedPrice(totalCartAmount));
            order.setDiscountedTotal(discountedTotal);
            log.info("Discounted total: " + order.getDiscountedTotal());
        }
        // If the number of drinks is more than 3, the cheapest drink is free:
        else if (drinks >= 3) {
            log.info("Cart has more than 3 items");
            discountedTotal = totalCartAmount - mininumPrice;
            order.setDiscountedTotal(discountedTotal);
            log.info("Discounted total: " + order.getDiscountedTotal());
        }

        else if (totalCartAmount > 12) {
            log.info("Cart total is more than 12 euros!");
            discountedTotal = getDiscountedPrice(totalCartAmount);
            order.setDiscountedTotal(discountedTotal);
            log.info("Discounted total: " + order.getDiscountedTotal());

        }
    }
}
