package com.example.Starbux.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {

    private static final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
  
      return args -> {
        log.info("Preloading " + repository.save(new Product("Black Coffee", "drink", 4)));
        log.info("Preloading " + repository.save(new Product("Latte", "drink", 5)));
        log.info("Preloading " + repository.save(new Product("Mocha", "drink", 6)));
        log.info("Preloading " + repository.save(new Product("Tea", "drink", 3)));
        log.info("Preloading " + repository.save(new Product("Milk", "topping", 2)));
        log.info("Preloading " + repository.save(new Product("Hazelnut syrup", "topping", 3)));
        log.info("Preloading " + repository.save(new Product("Chocolate Sauce", "topping", 5)));
        log.info("Preloading " + repository.save(new Product("Lemon", "topping", 2)));
      };
    }


}
