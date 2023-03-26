package com.unir.pizzaordersms.model.api;

import java.util.List;
import java.util.stream.Collectors;

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
public class Pizza {

	private String name;
	private Double price;
	private Boolean sale;
	private Boolean vegan;
	private String description;
	private List<String> ingredients;

}
