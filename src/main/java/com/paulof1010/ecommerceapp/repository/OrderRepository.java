package com.paulof1010.ecommerceapp.repository;

import com.paulof1010.ecommerceapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
