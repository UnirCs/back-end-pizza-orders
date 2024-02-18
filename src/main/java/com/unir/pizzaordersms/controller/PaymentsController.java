package com.unir.pizzaordersms.controller;

import com.stripe.exception.StripeException;
import com.unir.pizzaordersms.model.api.request.CreateOrderRequest;
import com.unir.pizzaordersms.model.api.response.CreateOrderResponse;
import com.unir.pizzaordersms.service.PizzaOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {

    private final PizzaOrdersService service;

    @PostMapping("/payments")
    public ResponseEntity<CreateOrderResponse> addPayment(@RequestBody CreateOrderRequest request) throws StripeException {

        CreateOrderResponse response = service.createOrder(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
