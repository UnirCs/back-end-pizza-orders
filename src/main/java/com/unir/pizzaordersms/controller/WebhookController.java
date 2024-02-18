package com.unir.pizzaordersms.controller;

import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.unir.pizzaordersms.event.EventManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class WebhookController {

    @Value("${stripe.webhookSecret}")
    private String endpointSecret;

    private final EventManager eventManager;

    @PostMapping("/events")
    public ResponseEntity<Void> receiveEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            log.error("Check mismatch of API version. Deserialization failed for Event id: " + event.getId());
            return ResponseEntity.badRequest().build();
        }

        eventManager.handleEvent(event.getType(), stripeObject);
        return ResponseEntity.ok().build();
    }
}
