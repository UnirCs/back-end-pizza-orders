package com.unir.pizzaordersms.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.unir.pizzaordersms.model.enums.CheckoutType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.checkout.successUrl}")
    private String successUrl;

    @Value("${stripe.checkout.cancelUrl}")
    private String cancelUrl;

    @Value("${stripe.checkout.returnUrl}")
    private String returnUrl;

    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        log.info("Stripe API Version: " + com.stripe.Stripe.API_VERSION);
        com.stripe.Stripe.apiKey = apiKey;
    }

    public Session createCheckoutSession(String product, Double price, String currency, CheckoutType checkoutType) throws StripeException {

        SessionCreateParams.LineItem priceItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPrice(getPrice(price, product, "eur").getId())
                .build();
        Session session;

        if (checkoutType.equals(CheckoutType.DELEGATED)) {
            session = Session.create(
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(successUrl)
                            .setCancelUrl(cancelUrl)
                            .addLineItem(priceItem)
                            .setPhoneNumberCollection(SessionCreateParams.PhoneNumberCollection.builder()
                                    .setEnabled(true)
                                    .build())
                            .setShippingAddressCollection(SessionCreateParams.ShippingAddressCollection.builder()
                                    .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.ES)
                                    .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.CO)
                                    .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.EC)
                                    .build())
                            .setCustomText(SessionCreateParams.CustomText.builder()
                                    .setShippingAddress(SessionCreateParams.CustomText.ShippingAddress.builder()
                                            .setMessage("Esto es un ejemplo para ver en clase. En ningún momento recibirás una Pizza en tu domicilio")
                                            .build())
                                    .setSubmit(SessionCreateParams.CustomText.Submit.builder()
                                            .setMessage(":) UNIR PIZZA (:")
                                            .build())
                                    .build())
                            .build()
            );
        } else {
            session = Session.create(
                    SessionCreateParams.builder()
                            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setReturnUrl(returnUrl)
                            .addLineItem(priceItem)
                            .setPhoneNumberCollection(SessionCreateParams.PhoneNumberCollection.builder()
                                    .setEnabled(true)
                                    .build())
                            .setShippingAddressCollection(SessionCreateParams.ShippingAddressCollection.builder()
                                    .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.ES)
                                    .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.CO)
                                    .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.EC)
                                    .build())
                            .setCustomText(SessionCreateParams.CustomText.builder()
                                    .setShippingAddress(SessionCreateParams.CustomText.ShippingAddress.builder()
                                            .setMessage("Esto es un ejemplo para ver en clase. En ningún momento recibirás una Pizza en tu domicilio")
                                            .build())
                                    .setSubmit(SessionCreateParams.CustomText.Submit.builder()
                                            .setMessage(":) UNIR PIZZA (:")
                                            .build())
                                    .build())
                            .build()
            );
        }
        return session;
    }

    @SneakyThrows
    private Price getPrice(Double price, String productId, String currency) {

        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency(currency)
                        .setBillingScheme(PriceCreateParams.BillingScheme.PER_UNIT)
                        .setUnitAmountDecimal(BigDecimal.valueOf(price * 100))
                        .setProduct(productId)
                        .build();
        return Price.create(params);
    }
}
