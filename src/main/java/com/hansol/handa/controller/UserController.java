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

	// 로그인 화면 호출
	@GetMapping("/login")
	public String login(String error, String logout, Integer challenge_id, Model model, RedirectAttributes rttr,
			HttpServletRequest request) {
		log.info("login---------------------------------------");

		// 아이디 비밀번호 입력 오류
		if (error != null) {

			rttr.addFlashAttribute("error", "아이디 또는 비밀번호를 잘못 입력했습니다.\n" + "입력하신 내용을 다시 확인해주세요.");

			return "redirect:/member/login";

		}

		if (logout != null) {
			model.addAttribute("logout", "Logout");
		}

		// 챌린지 참여하기에서 넘어온 경우
		if (challenge_id != null) {
			log.info("" + challenge_id);
			request.getSession().setAttribute("detail", challenge_id);
		}

		// 로그인 후 이전 페이지로 이동 - 이전 페이지로 되돌아가기 위한 Refer 헤더 값을 세션의 prePage attribute 로 저장
		String uri = request.getHeader("Referer");
		
		log.info("로그인 전 Refer: " + uri);
		
		if (uri != null && !uri.contains("/login")) {
			request.getSession().setAttribute("prePage", uri);
		}

		return "member/login";
	}

	// 회원 가입 화면 호출
	@GetMapping("/register")
	public String register() {
		log.info("register---------------------------------------");
		return "member/register";
	}

	// 회원가입 기능
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

	// 아이디 중복 체크 기능
	@ResponseBody
	@PostMapping("/idcheck")
	public int idcheck(String member_id) {

		log.info("아이디 체크-----------------------------------" + member_id);

		log.info("아이디 체크 결과 : " + userService.idcheck(member_id));

		// 0 : 가입 가능, 1 : 가입 불가 (중복 O)

		return userService.idcheck(member_id);
	}

	// 회원 정보 수정 화면 호출
	@PreAuthorize("principal.Username == #memberId")
	@GetMapping("/amend/{memberId}")
	public String amendmember(@PathVariable("memberId") String memberId, Model model) {

		UserVO user = userService.read(memberId);

		log.info("amend--------------------------------------------" + user);

		model.addAttribute("user", user);

		return "member/amend";
	}

	// 회원 정보 수정 기능
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

		return "redirect:/mypage?member_id=" + memberId;
	}

	// 로그아웃
	@GetMapping("/logout")
	public void logout() {
		log.info("logout-----------------------------------------");
	}

	// 권한 다를 경우
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {

		log.info("access Denied:" + auth);

		model.addAttribute("msg", "AccessDenied");

	}

	// 메일 인증이 안되었을 경우
	@GetMapping("/nonCertify")
	public String nonCertify() {

		return "member/nonCertify";
	}

	// 메일 인증하기 화면 호출
	@GetMapping("/certifyEmail")
	public String certifyEmail(HttpServletRequest request) {

		return "member/certifyEmail";
	}

	// 메일 전송 기능
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

		if (checkResult) { // 인증 성공
			// auth 값 ROLE_CERTIFY_USER로 변경
			UserVO vo = new UserVO();
			vo.setMember_id(member_id);
			vo.setAuth("ROLE_CERTIFY_USER");

			userService.updateAuth(vo);

			rttr.addFlashAttribute("msg", "certify-success");
			rttr.addFlashAttribute("memberId", vo.getMember_id());


		} else { // 인증 실패
			rttr.addFlashAttribute("msg", "certify-fail");
			rttr.addFlashAttribute("error", "메일 인증에 실패하였습니다. 인증을 다시 진행해주세요.");
			
		}
		return "redirect:/member/emailCheck";
	}

	// 메일 인증 완료 화면 호출
	@GetMapping("/emailCheck")
	public String emailCheck() {
		return "member/emailCheck";
	}

	// 아이디 찾기 기능
	@PostMapping("/find/id")
	public String findIDPOST(UserVO user, Model model) {

		log.info("find id-----------------------------");

		model.addAttribute("userList", userService.findID(user));

		model.addAttribute("msg", "find-result");

		model.addAttribute("type", "id");

		return "member/find";
	}

	// 비밀번호 찾기 기능
	@PostMapping("/find/pw")
	public @ResponseBody String findPWPOST(String member_id, String e_mail, Model model) {

		log.info("find pw-----------------------------");

		UserVO user = new UserVO();

		user.setMember_id(member_id);
		user.setE_mail(e_mail);

		boolean result = userService.findPW(user);

		model.addAttribute("type", "pw");

		if (result) {
			model.addAttribute("pw-result", "이메일로 임시 비밀번호를 발급했습니다. 발급받은 비밀번호로 로그인을 진행해주세요.");

			return "이메일로 임시 비밀번호를 발급했습니다.";
		} else {
			model.addAttribute("pw-result", "가입된 정보가 없거나, 입력하신 정보가 일치하지 않습니다.");

			return "가입된 정보가 없거나, 입력하신 정보가 일치하지 않습니다.";
		}
	}

	// 비밀번호 재발급 메일 전송 기능
	@PostMapping("/find/send-pw")
	public @ResponseBody String sendPWPOST(String member_id, String e_mail, Model model) {

		log.info("find pw-----------------------------");

		UserVO user = userService.read(member_id);

		try {

			userService.sendPWMail(user);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		model.addAttribute("msg", "find-result");

		return "member/find";
	}

	// 아이디 찾기 화면 호출
	@GetMapping("/find/id")
	public String findID(Model model) {

		model.addAttribute("type", "id");

		return "member/find";
	}

	// 비밀번호 찾기 화면 호출
	@GetMapping("/find/pw")
	public String findPW(Model model) {

		model.addAttribute("type", "pw");

		return "member/find";
	}

	// 비밀번호 변경 기능
	@PostMapping("/change-pw")
	public @ResponseBody String passwordChange(UserVO user) {

		log.info("change password:" + user.getPassword());

		userService.updatePW(user);

		return "change";
	}
}
