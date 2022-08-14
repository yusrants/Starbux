package com.example.Starbux.Product;

import java.util.Optional;
//Testing
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//
import net.bytebuddy.implementation.bytecode.collection.ArrayLength;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Long getProductIdfromName(String drinkName){
        Product product1 = productRepository.findByName(drinkName);
        return product1!=null ? product1.getId(): null ;
    }

    public Boolean verifyProduct(Product product) {
        Boolean verified;
        if (product.getName() != null && product.getPrice() != 0f && product.getCategory() != null) {
            verified = true;
        }
        else verified = false; 
        return verified;
    }

    public Boolean doesProductNameExist(Product product){
        String productName = product.getName();
        // Checking if product with the same name already exists:
        if (productRepository.findByName(productName) == null) return false;
        else return true;
    }

    public String createToppingsReport() {
        String report = "";
        List<Product> products = productRepository.findAll();
        for (Product product: products) {
        if( product.getMostUsedTopping()!=null) {
            if (product.getMostUsedTopping().size() > 0) {
            report += "Most used toppings for " + product.getName() + ":\n";
            int index = 0;
            List<Long> toppings = product.getMostUsedTopping();
            for(Long toppingId: toppings){
                index++;
                Optional<Product> topping = productRepository.findById(toppingId);
                    if (product != null){
                         report += index + ". " + topping.get().getName() + "\n";
            }}
          }}}
        return report; 
    }

}
