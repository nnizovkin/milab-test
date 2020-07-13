package com.milab.controller;

import com.milab.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RestController
public class CalculationController {
    @Autowired
    private CalculationService service;

    @PostMapping("/accumulate/{val}")
    private Mono<BigInteger> accumulate(@PathVariable("val") long val) {
        return service.accumulate(val);
    }

    @GetMapping("/calculate")
    private Mono<BigInteger> calculate() {
        return service.calculate();
    }
}
