package com.unir.pizzaordersms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unir.pizzaordersms.model.api.OrderRequest;
import com.unir.pizzaordersms.model.api.OrderStatus;
import com.unir.pizzaordersms.service.PizzaOrdersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PizzaOrdersController {

	private final PizzaOrdersService service;

	@GetMapping("/orders")
	/**
	 * Devuelve 200, 500
	 * @return
	 */
	public ResponseEntity<List<OrderRequest>> getOrders() {

		try {
			return ResponseEntity.ok(service.getAllOrders());
		} catch (Exception e) {
			log.error("Error retrieving orders {}", e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}

	}

	@PostMapping("/orders")
	/**
	 * Devuelve 201, 400, 500
	 * @param request
	 * @return
	 */
	public ResponseEntity<OrderRequest> addOrder(@RequestBody OrderRequest request) {

		try {
			
			OrderRequest created = service.createOrder(request);
			if (created == null) {
				return ResponseEntity.badRequest().build();
			} else {
				return ResponseEntity.status(HttpStatus.CREATED).body(created);
			}
		} catch (IllegalArgumentException e) {
			log.error("Invalid pizza received in the order {}", e.getMessage(), e);
			return ResponseEntity.badRequest().build();

		} catch (Exception e) {
			log.error("Error creating order {}", e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}

	}

	@PatchMapping("/orders/{idOrder}")
	/**
	 * Devuelve 200, 404, 500
	 * @param idOrder
	 * @param status
	 * @return
	 */
	public ResponseEntity<OrderRequest> modifyStatus(@PathVariable String idOrder, @RequestBody OrderStatus status) {

		try {
			
			OrderRequest prev = service.getOrderById(idOrder);
			if(prev == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(service.modifyOrderStatus(prev, status));
			}

		} catch (Exception e) {
			log.error("Error modifying order {}", e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}

	}

}
