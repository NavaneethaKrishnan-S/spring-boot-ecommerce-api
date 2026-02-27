package com.codewithnaveen.ecommerce.services;

import com.codewithnaveen.ecommerce.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}