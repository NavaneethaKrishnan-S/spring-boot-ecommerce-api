package com.codewithnaveen.ecommerce.payments;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    public PaymentException(String s) {
        super(s);
    }
}