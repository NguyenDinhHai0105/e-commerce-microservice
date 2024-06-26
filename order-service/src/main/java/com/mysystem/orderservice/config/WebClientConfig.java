package com.mysystem.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // automatic create a client-side load balancing for web client
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
