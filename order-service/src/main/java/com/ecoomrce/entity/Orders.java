package com.ecoomrce.entity;

import java.util.List;

import com.ecoomrce.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Enumerated(EnumType.STRING)
	OrderStatus orderStatus;
	Double price;
	@OneToMany(mappedBy="order",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	 List<OrderItem> items;
}
