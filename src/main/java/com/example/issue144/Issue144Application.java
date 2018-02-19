package com.example.issue144;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableWebFluxSecurity
public class Issue144Application {

    private static final Logger log = LoggerFactory.getLogger(Issue144Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Issue144Application.class, args);
    }

    @Bean
    MapReactiveUserDetailsService authentication() {
        return new MapReactiveUserDetailsService(
                User.withDefaultPasswordEncoder().username("user").password("pw").roles("USER").build()
        );
    }

    @Bean
    SecurityWebFilterChain authorization(ServerHttpSecurity security) {
        return security.authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("httpbin", r -> r
                        .order(8000)
                        .path("/**")
                        .filter((exchange, chain) ->
                            exchange
                                    .getPrincipal()
                                    .switchIfEmpty(Mono.just(() -> "empty"))
                                    .doOnSuccess(p -> log.info("principal.name: {}", p.getName()))
                                    .then(chain.filter(exchange))
                        )
                        .uri("http://httpbin.org:80")
                )
                .build();
    }
}
