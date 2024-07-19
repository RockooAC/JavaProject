package org.gbbv.musikkspring.repository;

import org.gbbv.musikkspring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(int userId);
}