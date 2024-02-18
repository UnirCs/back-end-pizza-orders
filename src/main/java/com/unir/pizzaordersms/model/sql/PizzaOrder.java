package com.unir.pizzaordersms.model.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PizzaOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "sessionId")
	private String sessionId;
	
	@Column(name = "price")
	private Double price;

	@ElementCollection
	private List<String> ingredients;
	
	//True si el pedido ha sido entregado, false e.o.c
	@Column(name = "delivered")
	private Boolean delivered;
	
	//True si las pizzas del pedido ya se han hecho, false e.o.c
	@Column(name = "processed")
	private String paymentStatus;
}
