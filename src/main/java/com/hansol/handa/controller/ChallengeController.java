package com.hansol.handa.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	// Model의 addAttribute 함수 사용이 안되어 ModelMap 사용
	// 맨 처음 화면은 전체 챌린지 리스트 조회
	public String list(ModelMap model) throws Exception{ 
		List<ChallengeVO> challengeList = challengeService.selectAllChallenge();
		model.addAttribute("challengeList", challengeList);
	  
		return "challenge/list"; 
	}
	 
	
	@GetMapping("/list")
	// 각 카테고리 별 챌린지 리스트 조회
	public String list(@RequestParam(required = false) String category, @RequestParam(required = false) String sortType,
				ModelMap model) throws Exception{
		
		//logger.info(category + " " + sortType);
		int categoryID = Integer.parseInt(category);
		
		List<ChallengeVO> challengeList = challengeService.selectChallengeList(categoryID);
		Map<String, String> categoryName = challengeService.selectCategoryName(categoryID);
		
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("subCategoryName", categoryName.get("sub_category_name"));
		model.addAttribute("mainCategoryName", categoryName.get("main_category_name"));
		
		return "challenge/list";
	}
	
	@GetMapping("/create")
	public String create() {
		System.out.println("create---------------------------------------");
		return "challenge/create";
	}
	
	@GetMapping("/detail")
	public String detail() {
		System.out.println("detail---------------------------------------");
		return "challenge/detail";
	}
	
}
