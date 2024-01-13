package com.fvthree.app.gateway;

import java.security.Key;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class PreFilter implements GatewayFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(PreFilter.class);

    @Autowired
    Environment env;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse errorResponse = exchange.getResponse();
        try {
            logger.info("My first Pre-filter is executed...");

            String requestPath = exchange.getRequest().getPath().toString();
            logger.info("Request path = " + requestPath);

            HttpHeaders headers = exchange.getRequest().getHeaders();

            Set<String> headerNames = headers.keySet();

            headerNames.forEach((headerName) -> {

                String headerValue = headers.getFirst(headerName);
                logger.info(headerName + " " + headerValue);

            });

            if (!request.getHeaders().containsKey(com.google.common.net.HttpHeaders.AUTHORIZATION)) {
                String responseBody =  stringResponseBody(errorResponse, "No authorization header");
                return errorResponse.writeWith(Mono.just(errorResponse.bufferFactory().wrap(responseBody.getBytes())));
            }

            String authorizationHeader = request.getHeaders().get(com.google.common.net.HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(jwt)) {
                String responseBody = stringResponseBody(errorResponse ,"JWT token is not valid");
                return errorResponse.writeWith(Mono.just(errorResponse.bufferFactory().wrap(responseBody.getBytes())));
            }

        } catch (Exception e) {
           logger.error("error: {}", e.getMessage());
           String responseBody;
            try {
                responseBody = stringResponseBody(errorResponse, e.getMessage());
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
            return errorResponse.writeWith(Mono.just(errorResponse.bufferFactory().wrap(responseBody.getBytes())));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private String stringResponseBody(ServerHttpResponse errorResponse, String message) throws JsonProcessingException {
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        errorResponse.getHeaders().add("Content-Type", "application/json");
        return new ObjectMapper().writeValueAsString(
                HttpResponse.builder()
                        .reason(message)
                        .message(message)
                        .status(HttpStatus.UNAUTHORIZED.name())
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build()
        );
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("token.secret")));

        try {

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            returnValue = false;
        }
        return returnValue;
    }

}
