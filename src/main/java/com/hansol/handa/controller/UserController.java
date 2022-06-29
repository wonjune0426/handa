package com.hansol.handa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class UserController {

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

	@GetMapping("/amend")
	public String amendmember() {
		log.info("amend--------------------------------------------");
		return "member/amend";
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
		return "sample/security-sample";
	}
	
	@GetMapping("/index")
	public String index() {
		// 챌린지 컨트롤러로 옮겨야함
		return "challenge/index";
	}
}
