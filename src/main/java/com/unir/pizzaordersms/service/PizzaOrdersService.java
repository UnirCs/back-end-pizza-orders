package com.unir.pizzaordersms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.unir.pizzaordersms.data.OrdersRepository;
import com.unir.pizzaordersms.model.api.request.CreateOrderRequest;
import com.unir.pizzaordersms.model.api.response.CreateOrderResponse;
import com.unir.pizzaordersms.model.api.request.ValidateOrderRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.unir.pizzaordersms.facade.PizzaCatalogueFacade;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PizzaOrdersService {

    private final OrdersRepository repository;
    private final PizzaCatalogueFacade facade;
    private final ObjectMapper objectMapper;
    private final StripeService stripeService;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        try {
            Session session = stripeService.createCheckoutSession(
                    request.getProduct(),
                    request.getPrice(), "eur",
                    request.getCheckoutType());
            repository.saveDraftOrder(request, session.getId());
            return new CreateOrderResponse(session.getUrl(), session.getClientSecret(), session.getId());
        } catch (StripeException e) {
            log.error("Error creating order: {}", e.getMessage(), e);
            return null;
        }
    }

    public Boolean validateOrder(ValidateOrderRequest request) {
        List<?> ingredients = request.getIngredients().stream().map(facade::headIngredient).filter(Objects::nonNull).toList();
        return ingredients.size() == request.getIngredients().size();
    }
}
