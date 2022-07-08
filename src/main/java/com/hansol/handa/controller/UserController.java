package com.hansol.handa.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
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
	
	@Autowired
	AuthenticationManager authenticationManager;

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
	public String registerPOST(UserVO userVO, RedirectAttributes rttr, Model model) {

		log.info("register post--------------------");


		try {
			userService.register(userVO);
			
			rttr.addFlashAttribute("msg", "register-success");
			
			model.addAttribute("msg", "before-send");
			
			// 메일 보내기
			model.addAttribute("user", userVO);

			log.info("가입 성공-----------------------------");

			return "member/certifyEmail";
			
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
	public String amendmemberPOST(@PathVariable("memberId") String memberId, UserVO userVO, RedirectAttributes rttr) {

		log.info("amend post--------------------------------------------" + userVO);
		
		int result = userService.amend(userVO);
		
		log.info("result: " + result);
		
		if (result == 1) {
			rttr.addFlashAttribute("msg", "amend-success");
			
		} else {
			rttr.addFlashAttribute("msg", "amend-fail");
		}
		
		return "redirect:/mypage";
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
	
	@GetMapping("/nonCertify")
	public String nonCertify() {
		
		return "member/nonCertify";
	}
	
	
	@GetMapping("/certifyEmail")
	public String certifyEmail(HttpServletRequest request) {
		
		return "member/certifyEmail";
	}
	
	@PostMapping("/sendMail")
	public String sendMail(UserVO user, Model model) throws MessagingException {
		
		log.info("user: " + user); 
		
		// 토큰 값을 서비스 쪽에서 생성해야함
		userService.sendMail(user);
		
		model.addAttribute("msg", "after-send");
		
		return "member/certifyEmail";
	}
	
	// 메일 인증 링크
	@GetMapping("/check-email-token")
	public String verifyEmail(String token, String member_id, RedirectAttributes rttr, Model model) {
		
		log.info("check-email-token Contorller: " + token + ", " + member_id);
		
		// 토큰 값 일치하는지 확인 (파라미터로 받은 토큰 값 == DB에서 불러온 토큰값) : 이 작업도 서비스에서 진행
		boolean checkResult = userService.checkEmailToken(token, member_id);
		
		if (checkResult) { 						// 인증 성공
			// auth 값 ROLE_CERTIFY_USER로 변경
			UserVO vo = new UserVO();
			vo.setMember_id(member_id);
			vo.setAuth("ROLE_CERTIFY_USER");
			
			userService.updateAuth(vo);
			
			rttr.addFlashAttribute("msg", "certify-success");
			rttr.addFlashAttribute("memberId", vo.getMember_id());
			
			return "redirect:/member/emailCheck";
			
		} else {								// 인증 실패
			rttr.addFlashAttribute("msg", "certify-fail");
			rttr.addFlashAttribute("error", "메일 인증에 실패하였습니다. 인증을 다시 진행해주세요.");
		}
		
		return "redirect:/member/login";
	}
	
	@GetMapping("/emailCheck")
	public String emailCheck() {
		return "member/emailCheck";
	}
	
	 @PostMapping("/find")
	 public String findPOST(String type) {
		 
		 if (type == "id") {
			 log.info("find id-----------------------------");
		 }
		 
		 if (type == "pw") {
			 log.info("find pw-----------------------------");
			 
		 }
		 
		 return "member/find";
	 }
	
	 @GetMapping("/find")
	 public String find() {
		 
		 return "member/find";
	 }
}
