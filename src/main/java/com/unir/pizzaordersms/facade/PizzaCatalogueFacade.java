package com.unir.pizzaordersms.facade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.unir.pizzaordersms.model.api.Pizza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PizzaCatalogueFacade {

	private final RestTemplate restTemplate;

	@Value("${unir.app.pizza.catalogue.url}")
	private String getPizzaUrl;

	public ResponseEntity<Pizza> getPizza(String idPizza) {
		try {
			return restTemplate.getForEntity(String.format(getPizzaUrl, idPizza), Pizza.class);
		} catch (HttpClientErrorException e) {
			log.error("Client Error: {}, Pizza with ID {}", e.getStatusCode(), idPizza);
			return ResponseEntity.badRequest().build();
		}
	}

}
