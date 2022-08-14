package com.example.Starbux.API.Admin;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Starbux.Customer.CustomerRepository;
import com.example.Starbux.OrderDTO.OrderDTO;
import com.example.Starbux.OrderDTO.OrderRepository;
import com.example.Starbux.Product.*;

@RestController
public class AdminController {
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private ProductService productService;

    AdminController(ProductRepository productRepo, OrderRepository orderRepo, CustomerRepository customerRepo) {
      this.productRepo = productRepo;
      this.orderRepo = orderRepo;
      this.customerRepo = customerRepo;
      this.productService = new ProductService(productRepo);
    }
  

    @GetMapping("/admin/products")
    List<Product> all() {
      return productRepo.findAll();
    }

    // Adding a new product i.e drink or toppping
    @PostMapping("/admin/products")
    ResponseEntity<String> newProduct(@RequestBody Product newProduct) {
        if (productService.verifyProduct(newProduct)) {

            // Checking if product with the same name already exists:
            if (!(productService.doesProductNameExist(newProduct))) {
                productRepo.save(newProduct);
                return ResponseEntity.status(HttpStatus.OK).body("Product added successfully! " + newProduct);}
            else {
                return ResponseEntity.badRequest()
                .body("Product with name " + newProduct.getName() +" already exists!");
            } }
        else {
            return ResponseEntity.badRequest()
            .body("Product can't be added without NAME, PRICE and CATEGORY!");
        }
    }
  
    // Single item
    @GetMapping("/admin/products/{id}")
    Product one(@PathVariable Long id) {
      
      return productRepo.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    }
  
    @PutMapping("/admin/products/{id}")
    ResponseEntity<String> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return productRepo.findById(id)
                .map(Product -> {
                    Boolean exists = false;
                    if (newProduct.getName() != null){
                        exists = productService.doesProductNameExist(newProduct);
                    }
                    if (!(exists)){
                        if (newProduct.getName() != null)
                            Product.setName(newProduct.getName());
                        if (newProduct.getCategory() != null)
                            Product.setCategory(newProduct.getCategory());
                        if (newProduct.getPrice() != 0f)
                            Product.setPrice(newProduct.getPrice());
                            productRepo.save(Product);
                        return ResponseEntity.status(HttpStatus.OK).body("Product edited successfully! " + Product);
                    }
                    else {
                        return ResponseEntity.badRequest()
                        .body("Product can't be edited. Product name already exists!");  
                    }
                })
                .orElseGet(() -> {
                    return ResponseEntity.badRequest()
                    .body("Product with the given id: " + id + " not found!");  
                });
    }
  
    @DeleteMapping("/admin/products/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if(!(productRepo.findById(id).isEmpty())) {
            productRepo.deleteById(id);
           return ResponseEntity.status(HttpStatus.OK).body("Product with id " + id + " deleted successfully! " );
        }
        else return ResponseEntity.badRequest()
        .body("Product with id: " + id + " does not exist!");  
    }

    @GetMapping("/admin/orders")
    List<OrderDTO> allOrders() {
      return orderRepo.findAll();
    }

    // _______________ Reports mapping  ___________

    @GetMapping("/admin/report/customers")
    String customerReports() {
      return customerRepo.findAll().toString();
    }

    @GetMapping("/admin/report/toppings")
    String mostUsedToppings() {
      String drinkReports = productService.createToppingsReport();
      if (drinkReports == "") {
            drinkReports = "Topping Reports are currently not available.";
      }
      return drinkReports;
    }
}
