package com.foodapp.order.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @GetMapping("/ping")
    public String ping() {
        return "order-ok";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create() {
        return UUID.randomUUID().toString();
    }
}
