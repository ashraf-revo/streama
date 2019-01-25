package org.revo.strema.clone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableDiscoveryClient
public class CloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloneApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> function(@Value("${message:default}") String message) {
        return route(GET("/"), req -> ServerResponse.ok().body(Mono.just(message), String.class));
    }
}

