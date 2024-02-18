package com.unir.pizzaordersms.event.checkout;

import com.stripe.model.checkout.Session;
import com.unir.pizzaordersms.event.ActionableEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("checkout.session.async_payment_succeeded")
@Slf4j
public class AsyncPaymentSucceeded extends ActionableEvent<Session> {

    @Override
    public void map(Session session) {
        log.info("Payment Succeeded for checkout session: " + session.getId());
    }
}
