package com.ecoomrce.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDto {
	Long id;
	Long productId;
	Integer quantity;
}
