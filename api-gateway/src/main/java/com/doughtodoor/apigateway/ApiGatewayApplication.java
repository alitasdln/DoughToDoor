package com.doughtodoor.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {
    @Bean
    public RouteLocator customRouteLocator (RouteLocatorBuilder builder) {

        return builder.routes().
                route("order-management", r -> r.path("/orders/**")
                    .uri("http://localhost:8088"))
                .route("user-management", r -> r.path("/users/**")
                    .uri("http://localhost:8080"))
                .build();
    }
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
