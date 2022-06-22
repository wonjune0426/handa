package com.hansol.handa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChallengeController {
	
	@GetMapping("/test")
	public String test() {
		return "Hello World";
	}
	
	@GetMapping({"/", "/list"})
	public String list() {
		System.out.println("index---------------------------------------");
		return "index";
	}
	
	@GetMapping("/create")
	public String create() {
		System.out.println("create---------------------------------------");
		return "create";
	}
	
	@GetMapping("/detail")
	public String detail() {
		System.out.println("detail---------------------------------------");
		return "detail";
	}
	
}
