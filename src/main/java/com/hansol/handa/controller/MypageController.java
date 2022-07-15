package com.hansol.handa.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansol.handa.domain.ChallengeVO;
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
	@PreAuthorize("principal.Username == #member_id")
	@GetMapping("")
	public String getIndex(@RequestParam String member_id, Model model) {
		UserVO memberInfo = mypageService.selectMemberInfo(member_id);					// 내 정보 조회
		
		int produceCount = mypageService.selectProduceCount(member_id);					// 생성 챌린지 개수
		int partCount = mypageService.selectPartCount(member_id);						// 참여 챌린지 개수
		
		List<ChallengeVO> listProduce = mypageService.selectProdeceLimit(member_id);	// 생성 챌린지 (3개)
		List<ChallengeVO> listPart = mypageService.selectPartLimit(member_id);			// 참여 챌린지 (3개)
		
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("produceCount", produceCount);
		model.addAttribute("partCount", partCount);
		model.addAttribute("listProduce", listProduce);
		model.addAttribute("listPart", listPart);
		
		return "mypage/index";
	}
	 
	// 마이 페이지 생성 챌린지 목록 보기
	@GetMapping("/challenge-produce")
	public String challengeProduce(@RequestParam String member_id, Model model) {
		List<ChallengeVO> list = mypageService.selectProduce(member_id);
		
		model.addAttribute("produceList", list);
	
		return "mypage/challengeProduce";
	}
	
	// 마이 페이지 참여 챌린지 목록 보기
 	@GetMapping("/challenge-part")
	public String challengePart(@RequestParam String member_id, Model model) {
		List<ChallengeVO> list = mypageService.selectPart(member_id);
		
		model.addAttribute("partList", list);
		
		return "mypage/challengePart";
	}
 	
 	// 마이페이지 일정 관리 (생성/참여 챌린지 목록 조회)
 	@GetMapping("/challenge-all")
 	@ResponseBody
 	public HashMap<String, Object> challengeAll(@RequestParam String member_id){
 		HashMap<String, Object> map = new HashMap<String, Object>();
 		List<ChallengeVO> listProduce = mypageService.selectProduce(member_id);
 		List<ChallengeVO> listPart = mypageService.selectPart(member_id);
 		
 		map.put("listProduce", listProduce);
 		map.put("listPart", listPart);
 		
 		return map;
 	}
}
