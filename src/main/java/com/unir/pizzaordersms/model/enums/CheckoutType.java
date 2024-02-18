package com.unir.pizzaordersms.model.enums;

import lombok.Getter;

@Getter
public enum CheckoutType {

    DELEGATED("delegated"), EMBED("embed");

    private final String type;

    CheckoutType(String type) {
        this.type = type;
    }
}
