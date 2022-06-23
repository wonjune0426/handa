package com.hansol.handa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class UserController {

	@GetMapping("/login")
	public String login() {
		System.out.println("login---------------------------------------");
		return "member/login";
	}
	
	@GetMapping("/register")
	public String register() {
		System.out.println("register---------------------------------------");
		return "member/register";
	}
	
	@GetMapping("/amend")
	public String amendmember() {
		System.out.print("amend--------------------------------------------");
		return "member/amend";
	}
	
}
