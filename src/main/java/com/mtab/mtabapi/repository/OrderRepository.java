package com.mtab.mtabapi.repository;

import com.mtab.mtabapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
