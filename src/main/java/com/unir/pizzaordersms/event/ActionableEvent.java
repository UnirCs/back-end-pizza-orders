package com.unir.pizzaordersms.event;

import com.stripe.model.StripeObject;

public abstract class ActionableEvent<T> {

    protected abstract void map(T event);

    void handleEvent(StripeObject event) {
        map((T) event);
    }

}
