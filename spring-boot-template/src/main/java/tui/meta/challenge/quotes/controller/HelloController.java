package tui.meta.challenge.quotes.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String sayHello(){
        return "Hello with Spring Boot and Java!";
    }
}