package com.example.Starbux.OrderDTO;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDTO, Long> {
}
