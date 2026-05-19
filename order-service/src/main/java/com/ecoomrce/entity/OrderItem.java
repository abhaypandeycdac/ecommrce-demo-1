package com.ecoomrce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long productId;
	Integer quantity;
	@ManyToOne()
	@JoinColumn(name="order_id")
	Orders order;
}
