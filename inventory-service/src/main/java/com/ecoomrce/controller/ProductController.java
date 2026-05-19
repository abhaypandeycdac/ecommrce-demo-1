package com.ecoomrce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoomrce.client.OrdersFeignClient;
import com.ecoomrce.dto.OrderRequestDto;
import com.ecoomrce.dto.ProductDto;
import com.ecoomrce.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/products")
@Slf4j
public class ProductController {
	ProductService productService;
//	DiscoveryClient discoveryClient;
//	RestClient restClient;
    OrdersFeignClient ordersFeignClient;
	@GetMapping("/fetchOrders")
	public String fetchFromOrderService(HttpServletRequest httpServletRequest) {
		log.info(httpServletRequest.getHeader("X-Customer-Header"));
		//ServiceInstance orderService = discoveryClient.getInstances("order-service").getFirst();
		//String url = orderService.getUri() + "/orders/core/helloOrders";
       // return restClient.get().uri(url).retrieve().body(String.class);
		return ordersFeignClient.helloOrders();
	}

	@PostMapping
	public ResponseEntity<ProductDto> createNewProdct(@RequestBody ProductDto productDto) {
		ProductDto createdProduct = productService.createProduct(productDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProdct() {
		List<ProductDto> productList = productService.getAllProduct();
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProdct(@PathVariable Long id, @RequestBody ProductDto productDto) {
		ProductDto pdto = productService.updateProduct(id, productDto);
		return ResponseEntity.status(HttpStatus.OK).body(pdto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
    
	@PutMapping("/reduce-stocks")
	public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto){
	  Double totalPrice	=productService.reduceStocks(orderRequestDto);
	  return ResponseEntity.ok(totalPrice);
	}
	
}
