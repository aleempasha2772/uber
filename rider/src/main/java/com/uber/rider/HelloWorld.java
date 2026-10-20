package com.uber.rider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);

	@GetMapping("/hello")
	public String hello() {
        log.info("Hello, Rider!");
		return "Hello, Rider!";
        
	}
}