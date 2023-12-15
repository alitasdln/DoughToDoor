package com.doughtdoor.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
public class ApiGatewayApplication {
    public RouteLocator customRouteLocator (RouteLocatorBuilder builder) {

        return builder.routes().
                route("order-management", r -> r.path("/orders/**")
                    .uri("http://localhost:8088"))
                .route("user-management", r -> r.path("/users/**")
                    .uri("http://localhost:8888"))
                .build();
    }

}
