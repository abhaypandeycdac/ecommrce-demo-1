package com.ecoomrce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecoomrce.entity.OrderItem;

public interface OrderItemRepository  extends JpaRepository<OrderItem,Long>{

}
