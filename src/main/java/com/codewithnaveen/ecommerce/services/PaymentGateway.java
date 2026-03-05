package com.codewithnaveen.ecommerce.services;

import com.codewithnaveen.ecommerce.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}