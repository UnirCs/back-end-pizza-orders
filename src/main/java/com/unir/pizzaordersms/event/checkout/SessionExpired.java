package com.unir.pizzaordersms.event.checkout;

import com.stripe.model.checkout.Session;
import com.unir.pizzaordersms.event.ActionableEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("checkout.session.expired")
@Slf4j
public class SessionExpired extends ActionableEvent<Session> {

    @Override
    public void map(Session session) {
        log.info("Session Expired for checkout session: " + session.getId());
    }
}
