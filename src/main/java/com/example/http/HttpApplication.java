package com.example.http;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@SpringBootApplication
public class HttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpApplication.class, args);
    }
    @Bean
    RouterFunction<ServerResponse> routes (GreetingsHandler handler) {

        return route()
                .GET("/greetings/{name}", handler::getGreetingsHandler)
                .GET("/greetingsStream/{name}" , handler::getGreetingsStreamHandler)
                .build();
    }

    @Component
    @RequiredArgsConstructor
    class GreetingsHandler{

        private final GreetingService greetingService;

        public Mono<ServerResponse> getGreetingsHandler (ServerRequest request) {

            var name = request.pathVariable("name");
            return ok()
                    .body(greetingService.greet(new GreetingRequest(name)),
                        GreetingResponse.class);
        }

        public Mono<ServerResponse> getGreetingsStreamHandler(ServerRequest request) {

            var name = request.pathVariable("name");
            return ok()
                    .contentType(TEXT_EVENT_STREAM)
                    .body(greetingService.greetMany(new GreetingRequest(name)),
                        GreetingResponse.class);
        }
    }
}
