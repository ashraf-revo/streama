package org.revo.streama.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Flux.fromIterable;

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "gateway")
@EnableCircuitBreaker
@Slf4j
public class GatewayApplication {
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> function(@Value("${message:default}") String message, DiscoveryClient discoveryClient, Environment environment) {
        return route(GET("/message"), serverRequest -> ok().body(Mono.just(message + " from " + environment.getProperty("HOSTNAME") + " receive " + Objects.requireNonNull(this.restTemplate.getForObject("http://clone/message", String.class))), String.class))
                .andRoute(GET("/services"), serverRequest -> ok().body(fromIterable(discoveryClient.getServices().stream().flatMap(it -> discoveryClient.getInstances(it).stream()).collect(Collectors.toList())), ServiceInstance.class));
    }

}

