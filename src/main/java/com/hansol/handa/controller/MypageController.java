package com.hansol.handa.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("mypage")
public class MypageController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/memberdetail")
	public String memberDatail() {
<<<<<<< HEAD
		logger.info("memberDetail-------------------------------");
		return "mypage/memberDetail";
	}
	
	@GetMapping("/challenge-produce")
	public String challengeProduce() {
		logger.info("challenge-produce-------------------------");
		return "mypage/challengeProduce";
=======
		System.out.println("memberDetail-------------------------------");
		return "mypage/memberDetail";
>>>>>>> 95dfc686c3c2e783e3e85c24ec92930b681cd992
	}
	 
}
