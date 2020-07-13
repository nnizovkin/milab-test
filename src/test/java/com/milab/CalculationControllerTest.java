package com.milab;

import com.milab.service.CalculationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@WebFluxTest
@Import(CalculationService.class)
public class CalculationControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void testAccumulate() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                webClient.post()
                        .uri("/accumulate/1000000000000000000")
                        .exchange();
            }).start();
        }

        Thread.sleep(1000);

        System.out.println(
                webClient.get()
                        .uri("/calculate")
                        .exchange()
                        .returnResult(BigInteger.class));

        new Thread(() -> {
            webClient.post()
                    .uri("/accumulate/1")
                    .exchange();
        }).start();

        Thread.sleep(1000);

        System.out.println(
                webClient.get()
                        .uri("/calculate")
                        .exchange()
                        .returnResult(BigInteger.class));
    }
}
