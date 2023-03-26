package com.unir.pizzaordersms.model.api;

import java.util.Arrays;
import java.util.List;

import com.unir.pizzaordersms.model.sql.PizzaOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {

	private Long number;
	private List<String> pizzas;
	private String address;
	private Boolean delivered;
	private Boolean processed;
	private Double price;
	
	public OrderRequest(PizzaOrder order) {
		this.number = order.getId();
		this.pizzas = Arrays.asList(order.getConcept().split(","));
		this.address = order.getAddress();
		this.delivered = order.getDelivered();
		this.processed = order.getProcessed();
		this.price = order.getPrice();
	}
	
}
