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
import com.hansol.handa.service.UserService;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;

@Controller
public class ChallengeController {
	
	@Autowired
	private ChallengeService challengeService;
	@Autowired
	private UserService userService;

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
	 */
	
	// 리스트 화면 호출
	@GetMapping("/list")
	public String list(@RequestParam(required = false) String category, @RequestParam(required = false) String sortType,
			Model model) {

		Boolean isCategory = true;

		// 정렬 조건이 주어지지 않을 경우 (최신순)
		if(sortType == null)
			model.addAttribute("sortType", sortType);
		else
			model.addAttribute("sortType", Integer.parseInt(sortType));
		
		// 카테고리 ID가 주어지지 않을 경우 (전체 조회)
		if(category == null) isCategory = false;
		else{
			int categoryID = Integer.parseInt(category);  
			Map<String, String> categoryName = challengeService.selectCategoryName(categoryID);
				
			model.addAttribute("categoryID", categoryID);
			model.addAttribute("subCategoryName", categoryName.get("sub_category_name"));
			model.addAttribute("mainCategoryName", categoryName.get("main_category_name"));
		}

		model.addAttribute("isCategory", isCategory);

		return "challenge/list";
	}
		
		
	@ResponseBody
	@GetMapping("/challenge-list")
	// 전체 리스트 정렬 & 각 카테고리 별 챌린지 리스트 조회, 정렬
	public HashMap<String, Object> listView(@RequestParam(required = false) String category, @RequestParam(required = false) String sortType,
			@RequestParam(required = false) String createdate, @RequestParam(required = false) String count, Model model) {
	
		HashMap<String, Object> map = new HashMap<>();
		int challengeCount = 0;
		
		if(!category.equals("0")){	// 카테고리 별 조회의 경우
			int categoryID = Integer.parseInt(category);  
			Map<String, String> categoryName = challengeService.selectCategoryName(categoryID);
			challengeCount = challengeService.selectCount(categoryID);
			
			map.put("subCategoryName", categoryName.get("sub_category_name")); 
		}
		else challengeCount = challengeService.selectCount(0);
		
		List<ChallengeVO> challengeList = challengeService.selectChallegeList(category, sortType, createdate, count);
		map.put("challengeList", challengeList);

		map.put("challengeCount", challengeCount);
		
		return map;
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
	
	@GetMapping("/challenge/detail")
	public String detail(@RequestParam(required = true) int challenge_id,Model model) {
		ChallengeVO challengeVO=challengeService.detailChallenge(challenge_id);
		model.addAttribute("challengeDetail",challengeVO);
		model.addAttribute("createMember",userService.read(challengeVO.getMember_id()));
		model.addAttribute("joinMembers",userService.joinMembers(challengeVO.getChallenge_id()));
		return "challenge/detail";
	}

}
