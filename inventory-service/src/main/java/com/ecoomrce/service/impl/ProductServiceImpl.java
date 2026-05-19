package com.ecoomrce.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecoomrce.dto.OrderItemDto;
import com.ecoomrce.dto.OrderRequestDto;
import com.ecoomrce.dto.ProductDto;
import com.ecoomrce.entities.Product;
import com.ecoomrce.repository.ProductRepository;
import com.ecoomrce.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
	ProductRepository productRepository;
	ModelMapper modelMapper;

	public ProductDto createProduct(ProductDto productDto) {
		log.info("create new product");
		Product product = modelMapper.map(productDto, Product.class);
		product = productRepository.save(product);
		log.info("create new  product and their id is {} ", product.getId());
		return modelMapper.map(product, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProduct() {
		log.info("fetching all products");
		List<Product> products = productRepository.findAll();
		return products.stream().map(e -> modelMapper.map(e, ProductDto.class)).toList();
	}

	@Override
	public ProductDto getProductById(Long id) {
		log.info("fetching  product By their id {} ", id);
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("product not found with this id : " + id));
		return modelMapper.map(product, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(Long id, ProductDto productDto) {
		if (isExist(id)) {
			log.info("fetching  product By their id {}", id);
			throw new RuntimeException("product not found with this id : " + id);
		}
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("product not found with this id : " + id));

		modelMapper.map(productDto, product);

		Product updatedProduct = productRepository.save(product);
		log.info("update  product By their id {}", updatedProduct.getId());
		return modelMapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void deleteProduct(Long id) {
		log.info("TRYING TO DELETE  product By their id {}", id);
		if (isExist(id))
			productRepository.deleteById(id);
		else
			throw new RuntimeException("product not found with this id : " + id);

	}

	private boolean isExist(Long id) {
		log.info("check the product is avalaible with this id {}", id);
		return productRepository.existsById(id);
	}

	@Transactional
	public Double reduceStocks(OrderRequestDto orderRequestDto) {

	    log.info("Reducing the stocks");

	    Double totalPrice = 0.0;

	    for (OrderItemDto item : orderRequestDto.getItems()) {

	        Long productId = item.getProductId();
	        Integer quantity = item.getQuantity();

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("product not found with id: " + productId));

	        if (product.getStock() < quantity) {
	            throw new RuntimeException("product not fulfilled for given quantity");
	        }

	        product.setStock(product.getStock() - quantity);

	        totalPrice += quantity * product.getPrice();
	    }

	    return totalPrice;
	}
}
