package com.unir.pizzaordersms.event.checkout;

import com.stripe.model.checkout.Session;
import com.unir.pizzaordersms.event.ActionableEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("checkout.session.async_payment_failed")
@Slf4j
public class AsyncPaymentFailed extends ActionableEvent<Session> {

    @Override
    public void map(Session session) {
        log.info("Payment failed for checkout session: " + session.getId());
    }
}
