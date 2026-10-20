package com.uber.trip_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorld {
	
	
	@GetMapping("/hello")
	public String hello() {
	return "trip service running!";
    }
}
