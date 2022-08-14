package com.example.Starbux.API.Admin;

import java.util.List;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Admin API Controller
@RestController
public class AdminController {
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    AdminController(ProductRepository productRepo, OrderRepository orderRepo, CustomerRepository customerRepo) {
      this.productRepo = productRepo;
      this.orderRepo = orderRepo;
      this.customerRepo = customerRepo;
      this.productService = new ProductService(productRepo);
    }
  
    // ___ Handling Products _____

    @GetMapping("/admin/products")
    List<Product> all() {
      return productRepo.findAll();
    }

    // Adding a new product i.e drink or toppping
    @PostMapping("/admin/products")
    ResponseEntity<String> newProduct(@RequestBody Product newProduct) {
        if (productService.verifyProduct(newProduct)) {
            log.info("Product verified successfully!");

            // Checking if product with the same name already exists:
            if (!(productService.doesProductNameExist(newProduct))) {
                productRepo.save(newProduct);
                log.info("Saved product with id " + newProduct.getId());
                return ResponseEntity.status(HttpStatus.OK).body("Product added successfully! " + newProduct);}
            else {
            // Making sure product names are unique
                log.info("Name " + newProduct.getName() +" already exists!");
                return ResponseEntity.badRequest()
                .body("Product with name " + newProduct.getName() +" already exists!");
            } }
        else {
            // In case the response body deosn't have any name and price for the product
            return ResponseEntity.badRequest()
            .body("Product can't be added without any name and price!!");
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
                    // In case the user is trying to edit the product name
                    if (newProduct.getName() != null){
                        exists = productService.doesProductNameExist(newProduct);
                    }
                    // Editing will only be allowed if the new product name is not already present
                    if (!(exists)){
                        log.info("Product " + newProduct.getName() +" doesn't already exist.");
                        if (newProduct.getName() != null)
                            Product.setName(newProduct.getName());
                        if (newProduct.getCategory() != null)
                            Product.setCategory(newProduct.getCategory());
                        if (newProduct.getPrice() != 0f)
                            Product.setPrice(newProduct.getPrice());
                            productRepo.save(Product);
                            log.info("Product edited with id " + newProduct.getId());
                        return ResponseEntity.status(HttpStatus.OK).body("Product edited successfully! " + Product);
                    }
                    else {
                        return ResponseEntity.badRequest()
                        .body("Product can't be edited. Product name already exists!");  
                    }
                })
                // In case the user is trying to edit a product that doesn't exist
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

    // Orders per Customer Report:
    @GetMapping("/admin/report/customers")
    String customerReports() {
      return customerRepo.findAll().toString();
    }

    // Popular toppings per drink Report:
    @GetMapping("/admin/report/toppings")
    String mostUsedToppings() {
      String drinkReports = productService.createToppingsReport();

      // In case the createToppingsReport returns an empty string,
      // no orders or orders with toppings have been placed yet
      if (drinkReports == "") {
            drinkReports = "Topping Reports are currently not available.";
      }
      return drinkReports;
    }
}
