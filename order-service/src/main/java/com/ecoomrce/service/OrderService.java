package com.ecoomrce.service;

import java.util.List;

import com.ecoomrce.dto.OrderRequestDto;

public interface OrderService {
 List<OrderRequestDto> getAllOrders();
 OrderRequestDto getOrderById(Long id);
 OrderRequestDto createOrder(OrderRequestDto orderRequestDto);
}
