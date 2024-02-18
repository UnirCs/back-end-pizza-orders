package com.unir.pizzaordersms.event;

import com.stripe.model.StripeObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class EventManager {

    /** Spring's map injection
     * Spring creates under the hood a map with all the beans of type ActionableEvent
     * The key is the name of the bean (or the package + Java class if there is no name)
     * The value is the bean itself
     */
    private final Map<String, ActionableEvent<? extends StripeObject>> events;

    public void handleEvent(String eventType, StripeObject event) {
        ActionableEvent<? extends StripeObject> eventHandler = events.get(eventType);
        if (eventHandler == null) {
            log.warn("No event handler found for event type: " + eventType);
        } else {
            eventHandler.handleEvent(event);
        }
    }
}
