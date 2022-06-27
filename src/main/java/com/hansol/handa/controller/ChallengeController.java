package com.hansol.handa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hansol.handa.domain.ChallengeVO;
import com.hansol.handa.mapper.ChallengeMapper;
import com.hansol.handa.service.ChallengeService;

import groovyjarjarpicocli.CommandLine.Model;

@Controller
public class ChallengeController {

	@Autowired 
	private ChallengeService challengeService;
	
	@GetMapping("/test")
	public String test() {
		return "Hello World";
	}
	
	@GetMapping({"/", "/list"})
	// Model 객체의 addAttribute 사용 안됨
	public String list(ModelMap model) throws Exception{
	//public List<ChallengeVO> list() throws Exception{
		//return challengeService.selectAllChallenge();
		List<ChallengeVO> challengeList = challengeService.selectAllChallenge();
		model.addAttribute("challengeList", challengeList);
		
		return "challenge/list";
	}
	
	@GetMapping("/list/{subcategory_name}/sortType={sortType}")
	public String listCategorySort(@PathVariable("subcategory_name") String subcategory_name
				, @PathVariable("sortType") int sortType, ModelMap model) {
		int subcategory_id = 0;
		
		if(subcategory_name.equals("movie"))
			subcategory_id = 1;
		else if(subcategory_name.equals("meeting"))
			subcategory_id = 2;
		else if(subcategory_name.equals("hiking"))
			subcategory_id = 3;
		else if(subcategory_name.equals("reading"))
			subcategory_id = 4;
		else if(subcategory_name.equals("qualification"))
			subcategory_id = 5;
		else if(subcategory_name.equals("language"))
			subcategory_id = 6;
		
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
