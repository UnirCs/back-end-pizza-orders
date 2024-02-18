package com.unir.pizzaordersms.model.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderResponse {
    private String url;
    private String clientSecret;
    private String sessionId;
}
