package com.unir.pizzaordersms.model.api.request;

import com.unir.pizzaordersms.model.enums.CheckoutType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderRequest {
    private String name;
    private String product;
    private Double price;
    private String address;
    private List<String> ingredients;
    private CheckoutType checkoutType;
}
