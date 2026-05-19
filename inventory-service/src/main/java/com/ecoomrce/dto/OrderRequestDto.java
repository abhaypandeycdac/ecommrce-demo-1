package com.ecoomrce.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDto {
	private Integer quantity;
	private List<OrderItemDto> items;
}
