package com.hansol.handa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hansol.handa.domain.UserVO;
import com.hansol.handa.service.UserService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class UserController {

	@Setter(onMethod_ = { @Autowired })
	private UserService userService;

	@GetMapping("/login")
	public String login(String error, String logout, Model model, RedirectAttributes rttr, HttpServletRequest request) {
		log.info("login---------------------------------------");

		log.info("error:" + error);
		log.info("logout:" + logout);

		if (error != null) {

			rttr.addFlashAttribute("error", "아이디 또는 비밀번호를 잘못 입력했습니다.\n" + "입력하신 내용을 다시 확인해주세요.");

			return "redirect:/member/login";

		}

		if (logout != null) {
			model.addAttribute("logout", "Logout");
		}

		// 로그인 후 이전 페이지로 이동 - 이전 페이지로 되돌아가기 위한 Refer 헤더 값을 세션의 prePage attribute 로 저장
		String uri = request.getHeader("Refer");
		if (uri != null && !uri.contains("/login")) {
			request.getSession().setAttribute("prePage", uri);
		}

		return "member/login";
	}

	@GetMapping("/register")
	public String register() {
		log.info("register---------------------------------------");
		return "member/register";
	}

	@PostMapping("/register")
	public String registerPOST(UserVO userVO, RedirectAttributes rttr) {

		log.info("register post--------------------");


		try {
			int result = userService.register(userVO);
			
			rttr.addFlashAttribute("msg", "register-success");

			log.info("가입 성공-----------------------------");

			return "redirect:/member/login";
			
			
		} catch (Exception e) {
			
			log.info("가입 실패-----------------------------------");
			e.printStackTrace();
			
			rttr.addFlashAttribute("msg", "register-fail");

			return "redirect:/member/register";
		}
		
	}
	
	@ResponseBody
	@PostMapping("/idcheck")
	public int idcheck(String member_id) {
		
		log.info("아이디 체크-----------------------------------" + member_id);
		
		log.info("아이디 체크 결과 : " + userService.idcheck(member_id));
		
		// 0 : 가입 가능, 1 : 가입 불가 (중복 O)
		
		return userService.idcheck(member_id);
	}

	@PreAuthorize("principal.Username == #memberId")
	@GetMapping("/amend/{memberId}")
	public String amendmember(@PathVariable("memberId") String memberId, Model model) {
	
		
		UserVO user = userService.read(memberId);

		log.info("amend--------------------------------------------" + user);
		
		model.addAttribute("user", user);
		
		return "member/amend";
	}
	
	
	@PreAuthorize("principal.Username == #userVO.member_id")
	@PostMapping("/amend/{memberId}")
	public String amendmemberPOST(@PathVariable("memberId") String memberId, UserVO userVO) {

		log.info("amend post--------------------------------------------" + userVO);
		
		userService.amend(userVO);
		
		return "redirect:/mypage/memberdetail";
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

	@GetMapping("sample")
	public String securitySample() {
		return "sample/detail";
	}

	@GetMapping("/index")
	public String index() {
		// 챌린지 컨트롤러로 옮겨야함
		return "challenge/index";
	}
}
