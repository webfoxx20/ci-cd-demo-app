package com.softnet.webserver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {
	Logger log = LoggerFactory.getLogger(HelloController.class);

	@GetMapping("/api/hello")
	Map<String, String> hello() {
		log.info("Regest hit the server");
		return Map.of("message", "Hello from the webserver");
	}

}
