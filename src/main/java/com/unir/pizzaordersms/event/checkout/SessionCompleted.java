package com.unir.pizzaordersms.event.checkout;

import com.stripe.model.checkout.Session;
import com.unir.pizzaordersms.data.OrdersRepository;
import com.unir.pizzaordersms.event.ActionableEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("checkout.session.completed")
@Slf4j
public class SessionCompleted extends ActionableEvent<Session> {

    private final OrdersRepository ordersRepository;

    @Override
    public void map(Session session) {
        log.info("Session Completed for checkout session: " + session.getId());
        ordersRepository.completeOrder(session);
    }
}
