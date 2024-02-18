package com.unir.pizzaordersms.facade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PizzaCatalogueFacade {

	private final RestTemplate restTemplate;

	@Value("${unir.app.pizza.catalogue.head-ingredient-url}")
	private String getIngredientUrl;

	public ResponseEntity<?> headIngredient(String idIngredient) {
		try {
			return restTemplate.exchange(
					String.format(getIngredientUrl, idIngredient),
					HttpMethod.HEAD,
					null,
					ResponseEntity.class);
		} catch (HttpClientErrorException e) {
			log.error("Client Error: {}, Pizza with ID {}", e.getStatusCode(), idIngredient);
			return null;
		}
	}

}
