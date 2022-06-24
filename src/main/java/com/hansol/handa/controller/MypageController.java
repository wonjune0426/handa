package com.hansol.handa.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("mypage")
// API 형식으로 변경할 예정? 
public class MypageController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/memberdetail")
	public String memberDatail() {

		logger.info("memberDetail-------------------------------");
		return "mypage/memberDetail";
	}
	
	@GetMapping("/challenge-produce")
	public String challengeProduce() {
		logger.info("challenge-produce-------------------------");
		return "mypage/challengeProduce";
	}
	
	@GetMapping("/challenge-part")
	public String challengePart() {
		logger.info("challenge-part-----------------");
		return "mypage/challengePart";
	}
	
	@GetMapping("/calendar")
	public String getCalendar() {
		logger.info("calendar-------------------");
		return "mypage/calendar";
	}
	 
}
