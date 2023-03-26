package com.unir.pizzaordersms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.unir.pizzaordersms.data.PizzaOrderRepository;
import com.unir.pizzaordersms.facade.PizzaCatalogueFacade;
import com.unir.pizzaordersms.model.api.OrderRequest;
import com.unir.pizzaordersms.model.api.OrderStatus;
import com.unir.pizzaordersms.model.api.Pizza;
import com.unir.pizzaordersms.model.sql.PizzaOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PizzaOrdersService {

	private final PizzaOrderRepository repository;
	private final PizzaCatalogueFacade facade;

	public List<OrderRequest> getAllOrders() {
		return repository.findAll().stream().map(OrderRequest::new).collect(Collectors.toList());
	}

	public OrderRequest createOrder(OrderRequest request) throws IllegalArgumentException {

		if (!StringUtils.isEmpty(request.getAddress()) && request.getPizzas() != null
				&& !request.getPizzas().isEmpty()) {

			PizzaOrder order = new PizzaOrder();
			order.setAddress(request.getAddress());

			StringBuilder builder = new StringBuilder();
			Double orderPrice = 0.0;

			for (String pizza : request.getPizzas()) {

				ResponseEntity<Pizza> pizzaFromCatalogueResponse = facade.getPizza(pizza);

				if (pizzaFromCatalogueResponse.getStatusCode().equals(HttpStatus.OK)) {

					Pizza pizzaFromCatalogue = pizzaFromCatalogueResponse.getBody();
					orderPrice += pizzaFromCatalogue.getPrice();
					builder.append(pizzaFromCatalogue.getName());
					builder.append(",");
				} else {
					throw new IllegalArgumentException("Invalid pizza received");
				}

			}
			order.setConcept(builder.length() > 0 ? builder.substring(0, builder.length() - 1) : "");
			order.setPrice(orderPrice);
			order.setDelivered(Boolean.FALSE);
			order.setProcessed(Boolean.FALSE);

			PizzaOrder saved = repository.save(order);

			request.setDelivered(Boolean.FALSE);
			request.setProcessed(Boolean.FALSE);
			request.setPrice(saved.getPrice());
			request.setNumber(saved.getId());

			return request;

		} else {
			return null;
		}

	}

	public OrderRequest getOrderById(String idOrder) {
		return repository.findById(Long.valueOf(idOrder)).map(OrderRequest::new).orElse(null);
	}

	public OrderRequest modifyOrderStatus(OrderRequest prevRequest, OrderStatus status) {
		
		PizzaOrder order = repository.findById(prevRequest.getNumber()).get();
		order.setDelivered(status.getDelivered());
		order.setProcessed(status.getProcessed());
		repository.save(order);
		
		prevRequest.setProcessed(status.getProcessed());
		prevRequest.setDelivered(status.getDelivered());
		return prevRequest;
	}

}
