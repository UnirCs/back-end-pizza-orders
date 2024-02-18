package com.unir.pizzaordersms.data;

import com.stripe.model.checkout.Session;
import com.unir.pizzaordersms.model.api.request.CreateOrderRequest;
import com.unir.pizzaordersms.model.sql.PizzaOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrdersRepository {

    private final PizzaOrderJpaRepository repository;

    public void saveOrUpdate(PizzaOrder order) {
        repository.save(order);
    }

    public PizzaOrder findBySessionId(String sessionId) {
        return repository.findBySessionId(sessionId).orElse(null);
    }

    public void saveDraftOrder(CreateOrderRequest request, String sessionId) {
        PizzaOrder draft = new PizzaOrder();
        draft.setSessionId(sessionId);
        draft.setPrice(request.getPrice());
        draft.setIngredients(request.getIngredients());
        draft.setPaymentStatus("Pending");
        draft.setDelivered(false);
        repository.save(draft);
    }

    public void completeOrder(Session session) {
        PizzaOrder order = repository.findBySessionId(session.getId()).orElse(null);
        if (order != null) {
            order.setPaymentStatus("Paid");
            repository.save(order);
        } else {
            log.error("Supposedly paid order not found in the database " + session.getId());
        }
    }
}
