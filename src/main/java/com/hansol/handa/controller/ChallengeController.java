package com.hansol.handa.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansol.handa.domain.ChallengeVO;
import com.hansol.handa.service.ChallengeService;

import ch.qos.logback.classic.Logger;

@Controller
public class ChallengeController {

	@Autowired
	private ChallengeService challengeService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@GetMapping("/test")
	public String test() {
		return "Hello World";
	}

	@GetMapping("/")
	// 메인 페이지
	public String index() {
		return "challenge/index";
	}

	/**
	 * @param category : 카테고리 ID (1 ~ 6)
	 * @param sortType : 정렬 타입 (0: 최신 순, 1: 오래된 순, 2: 참여 인원 순)
	 * @return : 챌린지 리스트
	 */

	@GetMapping("/list")
	// 전체 리스트 정렬 & 각 카테고리 별 챌린지 리스트 조회, 정렬
	// Model의 addAttribute 함수 사용이 안되어 ModelMap 사용
	public String list(@RequestParam(required = false) String category, @RequestParam(required = false) String sortType,
				ModelMap model) throws Exception{

		Boolean isCategory = true;
		
		if(sortType == null) sortType = "0";
		
		if(category == null) isCategory = false;
		else{
			int categoryID = Integer.parseInt(category);  
			Map<String, String> categoryName = challengeService.selectCategoryName(categoryID);
			
			model.addAttribute("categoryID", categoryID);
			model.addAttribute("subCategoryName", categoryName.get("sub_category_name"));
			model.addAttribute("mainCategoryName", categoryName.get("main_category_name"));
		}
		
		List<ChallengeVO> challengeList = challengeService.selectChallegeList(category, sortType);

		model.addAttribute("challengeList", challengeList);
		model.addAttribute("isCategory", isCategory); 
		model.addAttribute("sortType", sortType);
		
		return "challenge/list";
	}

	
//이미지 검색 api를 통한 이미지 검색
	@GetMapping("/imagelist/{searchWord}")
	@ResponseBody
	public List<String> imageList(@PathVariable String searchWord) {
		return challengeService.getimagelist(searchWord);
	}

//create 화면으로 이동
	@GetMapping("/create")
	public String create() {
		return "challenge/create";
	}
	
//challenge요청 데이터를 DB에 저장
	@PostMapping("/challenge")
	public @ResponseBody void createChallenge(ChallengeVO challengeVO) {
		challengeService.createChallenge(challengeVO);
	}

	
//challenge 수정화면 요청
	@GetMapping("/challenge-amend")
	public String challengeGet(@RequestParam(required = true) int challenge_id,Model model) {
		ChallengeVO challengeVO=challengeService.getChallenge(challenge_id);
		model.addAttribute("challenge",challengeVO);
		return "challenge/update";
	}
	
	
//challenge 수정 요청
	@PostMapping("/challenge-amend")
	public @ResponseBody void updateChallenge(ChallengeVO challengeVO) {
		challengeService.updateChallenge(challengeVO);
	}
	
	
//challenge 삭제
	@PostMapping("/challenge-remove")
	public @ResponseBody void deleteChallenge(ChallengeVO challengeVO) {
		challengeService.deleteChallenge(challengeVO);
	}
	
// challenge 참여하기
	@PostMapping("/challenge/member")
	public @ResponseBody void joinChallege(ChallengeVO challengeVO) {
		challengeService.joinChallenge(challengeVO.getMember_id(), challengeVO.getChallenge_id());
	}
	
// challenge 탈퇴하기
	@PostMapping("/challenge/challenge-secession")
	public @ResponseBody void secessionChallenge(ChallengeVO challengeVO) {
		challengeService.secessionChallenge(challengeVO);
	}
	
	@GetMapping("/detail")
	public String detail() {
		System.out.println("detail---------------------------------------");
		return "challenge/detail";
	}

}
