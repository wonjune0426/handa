package com.hansol.handa.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class UserController {

	@GetMapping("/login")
	public String login(String error, String logout, Model model) {
		log.info("login---------------------------------------");

		log.info("error:" + error);
		log.info("logout:" + logout);
		
		log.info("model: " + model);

		if (error != null) {

			model.addAttribute("error", "Login Error Check User Account");
		}
		if (logout != null) {
			model.addAttribute("logout", "Logout");
		}

		return "member/login";
	}

	@GetMapping("/register")
	public String register() {
		log.info("register---------------------------------------");
		return "member/register";
	}

	@GetMapping("/amend")
	public String amendmember() {
		log.info("amend--------------------------------------------");
		return "member/amend";
	}

	@GetMapping("/logout")
	public void logout() {
		log.info("logout-----------------------------------------");
	}

	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {

		log.info("access Denied:" + auth);

		model.addAttribute("msg", "AccessDenied");

	}

}
