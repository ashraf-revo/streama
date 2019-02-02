package org.revo.strema.clone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Flux.fromIterable;

@SpringBootApplication
@EnableDiscoveryClient
public class CloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloneApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> function(@Value("${message:default}") String message, DiscoveryClient discoveryClient, Environment environment) {
        return route(GET("/message"), serverRequest -> ok().body(Mono.just(message + " from " + environment.getProperty("HOSTNAME")), String.class))
                .andRoute(GET("/services"), serverRequest -> ok().body(fromIterable(discoveryClient.getServices().stream().flatMap(it -> discoveryClient.getInstances(it).stream()).collect(Collectors.toList())), ServiceInstance.class));
    }
}

