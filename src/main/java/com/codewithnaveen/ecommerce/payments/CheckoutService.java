package com.codewithnaveen.ecommerce.payments;

import com.codewithnaveen.ecommerce.entities.Order;
import com.codewithnaveen.ecommerce.exceptions.CartEmptyException;
import com.codewithnaveen.ecommerce.exceptions.CartNotFoundException;
import com.codewithnaveen.ecommerce.repositories.CartRepository;
import com.codewithnaveen.ecommerce.repositories.OrderRepository;
import com.codewithnaveen.ecommerce.services.AuthService;
import com.codewithnaveen.ecommerce.services.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        if(cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        try{
            var session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        }
        catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });
    }
}