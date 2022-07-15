package com.hansol.handa.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final RequestCache requestCache = new HttpSessionRequestCache();
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		clearSession(request);

		log.warn("Login Success");

		List<String> roleNames = new ArrayList<>();

		auth.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		// 이전 링크가 detail인 경우 (상세 페이지에서 넘어온 경우)
		Integer detail = (Integer)request.getSession().getAttribute("detail");
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		String prePage = (String) request.getSession().getAttribute("prePage");

		
		// 메일 미인증 유저
		if (roleNames.contains("ROLE_USER")) {
		
			// 메인, 리스트, 상세 제외하고 모두 인증화면으로 이동하기
			response.sendRedirect("/member/nonCertify");					
		}

		// 메일 인증된 유저인 경우
		if (roleNames.contains("ROLE_CERTIFY_USER")) {
			
			if (detail != null) {
				response.sendRedirect("/challenge/detail?challenge_id=" + detail);
				
				return;
			}


			// 로그인 후 직전 페이지로 이동
			// prePage가 존재하는 경우 사용자가 직접 /member/login 경로로 로그인 요청
			// 기존 Session의 prePage attribute 제거
			
			if (prePage != null) {
				request.getSession().removeAttribute("prePage");
			}
			
			

			// 기본 uri
			String uri = "";
			
			log.info("prePage: " + prePage);
			log.info("savedRequest: " + savedRequest);

			// savedRequest 가 존재하는 경우 = 인증 권한이 있는 페이지 접근
			// Security Filter가 인터셉트하여 savedRquest에 세션 저장
			if (savedRequest != null) {
				uri = savedRequest.getRedirectUrl();
				
				log.info("인터셉트한 uri: " + uri);
				
			} else if (prePage != null && !prePage.equals("")) {
				
				uri = prePage;
				
				// 회원가입 -> 로그인으로 넘어온 경우 "/"로 redirect
				if (prePage.contains("/member") || prePage.contains("/mypage") || prePage.contains("/challenge-amend")) {
					uri = "/";
				} 
				
					
				
			}

			redirectStrategy.sendRedirect(request, response, uri);
		}

	}

	// 로그인 실패 후 성공 시 남아있는 에러 세션 제거
	protected void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
