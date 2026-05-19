package com.ecoomrce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoomrce.config.FeaturesEnableConfig;
import com.ecoomrce.dto.OrderRequestDto;
import com.ecoomrce.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RefreshScope
public class OrderController {
	private final OrderService orderService;
	private final FeaturesEnableConfig featuresEnableConfig;
	@Value("${my.variable}")
	private String myvar;

	@GetMapping("/helloOrders")
	public String helloOrders(@RequestHeader("X-User-Id") Long userId) {
		if(featuresEnableConfig.isUserTrackingEnabled()) {
			return "topa singh tight"+myvar;
		}else {
			return "topa jhul gya"+myvar;
		}
		//return "hello from order Service"+userId;
	}

	@PostMapping("/create-order")
	public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto OrderRequestDto) {
		OrderRequestDto order = orderService.createOrder(OrderRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}

	@GetMapping
	public ResponseEntity<List<OrderRequestDto>> getAllOrders() {
		log.info("Fetching all orders via Cotroller");
		List<OrderRequestDto> orders = orderService.getAllOrders();
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
		log.info("Fetching all orders via Cotroller");
		OrderRequestDto order = orderService.getOrderById(id);
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}

}
