package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.dtos.CheckoutRequest;
import com.codewithnaveen.ecommerce.dtos.CheckoutResponse;
import com.codewithnaveen.ecommerce.dtos.ErrorDto;
import com.codewithnaveen.ecommerce.exceptions.CartEmptyException;
import com.codewithnaveen.ecommerce.exceptions.CartNotFoundException;
import com.codewithnaveen.ecommerce.services.CheckoutService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request){

        try {
            return ResponseEntity.ok(checkoutService.checkout(request));
        } catch (StripeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto("Error creating a checkout session"));
        }
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
