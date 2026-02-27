package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.dtos.ErrorDto;
import com.codewithnaveen.ecommerce.dtos.OrderDto;
import com.codewithnaveen.ecommerce.exceptions.OrderNotFoundException;
import com.codewithnaveen.ecommerce.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDenied(Exception ex){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto(ex.getMessage()));
    }

}