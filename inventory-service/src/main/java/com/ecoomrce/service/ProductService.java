package com.ecoomrce.service;

import java.util.List;

import com.ecoomrce.dto.OrderRequestDto;
import com.ecoomrce.dto.ProductDto;

public interface ProductService {
	ProductDto createProduct(ProductDto productDto);

	List<ProductDto> getAllProduct();

	ProductDto getProductById(Long id);

	ProductDto updateProduct(Long id, ProductDto productDto);

	void deleteProduct(Long id);

	Double reduceStocks(OrderRequestDto orderRequestDto);
}
