package com.fvthree.app.gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Autowired
    private final PreFilter filter;

    public SecurityConfig(PreFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){

        serverHttpSecurity.cors().and().csrf().disable()
                .authorizeExchange(exchange -> exchange
                        .anyExchange()
                        .permitAll());
        return serverHttpSecurity.build();
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTH-SERVICE", r -> r.path("/auth/**").uri("lb://AUTH-SERVICE"))
                .route("BOOK-SERVICE", r -> r.path("/book/**").filters(f -> f.filter(filter)).uri("lb://BOOK-SERVICE"))
                .route("REVIEW-SERVICE", r -> r.path("/review/**").filters(f -> f.filter(filter)).uri("lb://REVIEW-SERVICE"))
                .route("MESSAGE-SERVICE", r -> r.path("/message/**").filters(f -> f.filter(filter)).uri("lb://MESSAGE-SERVICE")).build();
    }

}
