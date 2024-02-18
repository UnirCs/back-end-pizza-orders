package com.unir.pizzaordersms.controller;

import com.unir.pizzaordersms.model.api.request.ValidateOrderRequest;
import com.unir.pizzaordersms.model.api.response.ValidateOrderResponse;
import com.unir.pizzaordersms.service.PizzaOrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidationsController {

    private final PizzaOrdersService service;

    @PostMapping("/validations")
    public ResponseEntity<ValidateOrderResponse> validateOrder(@RequestBody ValidateOrderRequest request) {
        return ResponseEntity.ok(new ValidateOrderResponse(service.validateOrder(request)));
    }
}
