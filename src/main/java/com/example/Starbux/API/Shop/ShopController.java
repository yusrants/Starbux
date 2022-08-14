package com.example.Starbux.API.Shop;

import com.example.Starbux.Customer.Customer;
import com.example.Starbux.Customer.CustomerRepository;
import com.example.Starbux.Customer.CustomerService;

import java.util.Random;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Starbux.OrderDTO.*;
import com.example.Starbux.Product.*;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ShopController {

    private final OrderRepository repository;
    private final CustomerRepository customerRepository;
    private OrderService orderService;
    private CustomerService customerService;

    private static final Logger log = LoggerFactory.getLogger(ShopController.class);

    ShopController(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
      this.repository = orderRepository;
      this.customerRepository = customerRepository;
      this.orderService = new OrderService(productRepository);
      this.customerService = new CustomerService(customerRepository);
    }
  

    // end::get-aggregate-root[]
  
    @PostMapping("/shop/placeOrder")
    ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderDTO newOrder) {
      OrderResponse response = new OrderResponse();

      if(orderService.verifyOrder(newOrder)) {
            repository.save(newOrder);
            
            // Calculating cart total for the order
            orderService.calculateTotal(newOrder);

            // Assigning the order to respective customer
            Customer customer = customerRepository.getCustomerByEmail(newOrder.getCustomer());

            if (customer!= null) {
                customer.addOrder(newOrder);
                log.info("Added order : " + newOrder.getId() + " to the customer");
                customerService.saveCustomer(customer);
                log.info("Customer already present with id : " + customer.getId());
            }
            else {
                Customer newCustomer = new Customer(newOrder.getCustomer());
                newCustomer.addOrder(newOrder);
                log.info("Added order : " + newOrder.getId() + " to the customer");
                customerService.saveCustomer(newCustomer);
                log.info("Customer saved with id : " + newCustomer.getId());
            }

            // Setting values for the response object
            response.setTotal(newOrder.getTotal());
            response.setdiscountedTotal(newOrder.getDiscountedTotal());
            response.setDate(LocalDate.now().toString());
            response.setInvoiceNumber(new Random().nextInt(1000));
            response.setItems(newOrder.getItems());
            response.setOrderId(newOrder.getId());
            response.setMessage("Order received! Thanks for shopping with us.");
            return ResponseEntity.ok(response);
      }
      else {
        response.setMessage("Invalid Order Object Format. Customer email/username or items not received!");
        return ResponseEntity.ok(response);
      }
    }
}

