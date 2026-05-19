package com.ecoomrce.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecoomrce.client.InventoryFeignClient;
import com.ecoomrce.dto.OrderRequestDto;
import com.ecoomrce.entity.OrderItem;
import com.ecoomrce.entity.Orders;
import com.ecoomrce.enums.OrderStatus;
import com.ecoomrce.repository.OrderRepository;
import com.ecoomrce.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
	ModelMapper modelMapper;
	OrderRepository OrderRepository;
	InventoryFeignClient inventoryFeignClient;
	@Override
	public List<OrderRequestDto> getAllOrders() {
		log.info("fetching all orders");
		List<Orders> orders = OrderRepository.findAll();
		return orders.stream().map(o -> modelMapper.map(o, OrderRequestDto.class)).toList();
	}

	@Override
	public OrderRequestDto getOrderById(Long id) {
		log.info("fetching  order with id : {}", id);
		Orders order = OrderRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("order not found with id : {}" + id));
		return modelMapper.map(order, OrderRequestDto.class);
	}

	
	@Override
	//@RateLimiter(name="inventoryRateLimiter",fallbackMethod="createOrderFallBack")
	//@Retry(name="inventoryRetry",fallbackMethod="createOrderFallBack")
	@CircuitBreaker(name="inventoryCircuitBreaker",fallbackMethod="createOrderFallBack")
	public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
		Double totalPrice=inventoryFeignClient.reduceStocks(orderRequestDto);
		Orders order=modelMapper.map(orderRequestDto, Orders.class);
		for(OrderItem orderItem:order.getItems()) {
			orderItem.setOrder(order);
		}
		order.setPrice(totalPrice);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		Orders savedOrder=OrderRepository.save(order);
		return modelMapper.map(savedOrder,OrderRequestDto.class);
	}

	public OrderRequestDto createOrderFallBack(OrderRequestDto orderRequestDto,Throwable throwable) {
		log.info("fallback occured due to : {}", throwable.getMessage());
		return new OrderRequestDto();
	}
}
