package com.ecoomrce.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequestDto {
	Long id;
	List<OrderItemDto> items;
	Double price;
}
