package com.hansol.handa.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	// Model의 addAttribute 함수 사용이 안되어 ModelMap 사용
	// 맨 처음 화면은 전체 챌린지 리스트 최신 순 조회 
	public String list(ModelMap model) throws Exception{ 
		List<ChallengeVO> challengeList = challengeService.selectAllChallenge();
		model.addAttribute("challengeList", challengeList);
	  
		return "challenge/list"; 
	}

	/**
	 * @param category : 카테고리 ID (1 ~ 6)
	 * @param sortType : 정렬 타입 (0: 최신 순, 1: 오래된 순, 2: 참여 인원 순)
	 * @return : 챌린지 리스트
	 */
	
	@GetMapping("/list")
	// 전체 리스트 정렬 & 각 카테고리 별 챌린지 리스트 조회, 정렬
	public String list(@RequestParam(required = false) String category, @RequestParam(required = false) String sortType,
				ModelMap model) throws Exception{
		
		List<ChallengeVO> challengeList = null;
		Boolean isCategory = true;	// 카테고리 리스트 조회 인지 아닌지
		
		if(category == null) {
			isCategory = false;	// 전체 리스트 조회일 경우
			
			switch(sortType) {
				case "0":	// 최신 순
					challengeList = challengeService.selectAllChallenge();
					break;
				case "1":	// 오래된 순
					challengeList = challengeService.selectAllChallengeDesc();
					break;
				case "2":	// 참여 인원 순
					break;
			}
		}
		else {
			int categoryID = Integer.parseInt(category);
			Map<String, String> categoryName = challengeService.selectCategoryName(categoryID);
			
			switch(sortType) {
				case "0":	// 최신 순
					challengeList = challengeService.selectChallengeList(categoryID);
					break;
				case "1": 	// 오래된 순
					challengeList = challengeService.selectChallengeListDesc(categoryID);
					break;
				case "2":	// 참여 인원 순
					break;
			}
			
			model.addAttribute("categoryID", categoryID);
			model.addAttribute("subCategoryName", categoryName.get("sub_category_name"));
			model.addAttribute("mainCategoryName", categoryName.get("main_category_name"));
		}

		model.addAttribute("challengeList", challengeList);
		model.addAttribute("isCategory", isCategory);

		return "challenge/list";
	}
	
	@GetMapping("/imagelist/{searchWord}")
	@ResponseBody
	public List<String> imageList(@PathVariable String searchWord){
		return challengeService.getimagelist(searchWord);
	}
	
	@GetMapping("/create")
	public String create() {
		return "challenge/create";
	}
	
	@PostMapping("/challenge")
	public String createChallenge(ChallengeVO challengeVO) {
		System.out.println(challengeVO.getChallenge_name());
		System.out.println(challengeVO.getThumbnail());
		System.out.println(challengeVO.getStartdate());
		System.out.println(challengeVO.getEnddate());
		System.out.println(challengeVO.getDescription());
		System.out.println(challengeVO.getSubcategory_id());
		System.out.println(challengeVO.getChallenge_type());
		challengeService.createChallenge(challengeVO);
		return "redirect:/";
	}
	
	@GetMapping("/detail")
	public String detail() {
		System.out.println("detail---------------------------------------");
		return "challenge/detail";
	}
	
}
