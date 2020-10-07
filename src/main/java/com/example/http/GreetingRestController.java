package com.example.http;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
class GreetingRestController {

    private final GreetingService greetingService;

    @GetMapping("/greeting/{name}")
    Mono<GreetingResponse> greet (@PathVariable String name) {
        return greetingService.greet(new GreetingRequest(name));
    }
}