package com.hansol.handa.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hansol.handa.domain.UserVO;
import com.hansol.handa.service.MypageService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("mypage")
public class MypageController {
	
	@Autowired
	private MypageService mypageService;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	// 마이 페이지 화면
	@GetMapping("")
	public String getIndex(@RequestParam String member_id, Model model) {
		UserVO memberInfo = mypageService.selectMemberInfo(member_id);
		int produceCount = mypageService.selectProduceCount(member_id);
		int partCount = mypageService.selectPartCount(member_id);
		
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("produceCount", produceCount);
		model.addAttribute("partCount", partCount);
		
		return "mypage/index";
	}
	 
	// 마이 페이지 생성 챌린지 목록 보기
	@GetMapping("/challenge-produce")
	public String challengeProduce() {
		logger.info("challenge-produce-------------------------");
		return "mypage/challengeProduce";
	}
	
	// 마이 페이지 참여 챌린지 목록 보기
 	@GetMapping("/challenge-part")
	public String challengePart() {
		logger.info("challenge-part-----------------");
		return "mypage/challengePart";
	}
}
