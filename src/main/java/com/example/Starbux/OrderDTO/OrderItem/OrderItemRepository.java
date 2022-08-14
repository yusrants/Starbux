package com.example.Starbux.OrderDTO.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}