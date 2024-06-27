package com.victorferx.orders.repositories;

import com.victorferx.orders.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
