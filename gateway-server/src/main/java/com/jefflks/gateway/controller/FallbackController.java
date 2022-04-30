package com.jefflks.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
	
    @GetMapping("/CommonService")
    @HystrixCommand
    public ResponseEntity<String> commonServiceFallBack() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        		.body("Common Service is taking longer than Expected. Please try again later");
    }

}
