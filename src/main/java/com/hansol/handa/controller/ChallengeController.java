package com.hansol.handa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChallengeController {
	
	@GetMapping("/test")
	public String test() {
		return "Hello World";
	}
	
}
