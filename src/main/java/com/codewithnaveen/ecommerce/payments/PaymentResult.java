package com.codewithnaveen.ecommerce.payments;

import com.codewithnaveen.ecommerce.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
