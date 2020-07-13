package com.milab.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CalculationService {
    private final Object monitor = new Object();
    private final List<CompletableFuture<BigInteger>> futures = new ArrayList<>();
    private BigInteger res = BigInteger.valueOf(0);
    public Mono<BigInteger> accumulate(long val) {
        synchronized (monitor) {
            CompletableFuture<BigInteger> future = new CompletableFuture<>();
            futures.add(future);
            res = res.add(BigInteger.valueOf(val));
            return Mono.fromFuture(future);
        }
    }

    public Mono<BigInteger> calculate() {
        synchronized (monitor) {
            for (CompletableFuture<BigInteger> future: futures) {
                future.complete(res);
            }

            futures.clear();
            Mono<BigInteger> monoRes = Mono.just(res);

            res = BigInteger.valueOf(0);
            return monoRes;
        }
    }
}
