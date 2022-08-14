package com.example.Starbux.API.Shop;

import com.example.Starbux.Customer.Customer;
import com.example.Starbux.Customer.CustomerRepository;
import com.example.Starbux.Customer.CustomerService;

import java.util.Random;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
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

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private OrderService orderService;
    private CustomerService customerService;

    private static final Logger log = LoggerFactory.getLogger(ShopController.class);

    ShopController(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
          this.orderRepository = orderRepository;
          this.customerRepository = customerRepository;
          this.productRepository = productRepository;
          this.orderService = new OrderService(productRepository);
          this.customerService = new CustomerService(customerRepository);
    }
  

    @GetMapping("/")
    ResponseEntity<String> all() {
      String Msg = "Welcome to Starbux \n";
      Msg += "You can order any drink with any number of toppings!" + "\n";
      Msg += "Here's a list of our products:" + "\n" ;
      Msg += productRepository.findAll().toString();
      return ResponseEntity.ok(Msg);
    }  

    // Handling POST request for placing orders:
    @PostMapping("/shop/placeOrder")
    ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderDTO newOrder) {
      OrderResponse response = new OrderResponse();

      // First verify the order to make sure any invalid orders are not saved in the repo
      if(orderService.verifyOrder(newOrder)) {
            orderRepository.save(newOrder);
            
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
        log.info("Invalid Request body received!");
        response.setMessage("Invalid Order Object Format. Customer email/username or items not received!");
        return ResponseEntity.ok(response);
      }
    }
}

