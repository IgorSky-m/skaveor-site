package com.skachko.gatewayservice;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;

import java.util.Formatter;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		Formatter formatter = new Formatter();
		Formatter s = formatter.format("ASDF%s", "ss");
		System.out.println(s.out());
		SpringApplication.run(GatewayServiceApplication.class, args);
		float f = 2.5f;
		int i = (int)f;
	}

	//@Bean
	public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry) {
		return new ReactiveResilience4JCircuitBreakerFactory(circuitBreakerRegistry, timeLimiterRegistry);
	}
}
