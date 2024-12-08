package com.web.seo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApi {
    @GetMapping("/sample")
    public String sampleEndpoint() {
        return "Hello, World!";
    }
}
