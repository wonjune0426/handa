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

		if (detail != null) {
			response.sendRedirect("/challenge/detail?challenge_id=" + detail);
			
			return;
		}
		
		if (roleNames.contains("ROLE_USER")) {
			response.sendRedirect("/member/nonCertify");
		}

		if (roleNames.contains("ROLE_CERTIFY_USER")) {

			SavedRequest savedRequest = requestCache.getRequest(request, response);

			// 로그인 후 직전 페이지로 이동
			// prePage가 존재하는 경우 사용자가 직접 /member/login 경로로 로그인 요청
			// 기존 Session의 prePage attribute 제거
			String prePage = (String) request.getSession().getAttribute("prePage");
			

			// log.info("이전 페이지: " + prePage);

			if (prePage != null) {
				request.getSession().removeAttribute("prePage");
			}
			
			

			// 기본 uri
			String uri = "/";

			// savedRequest 가 존재하는 경우 = 인증 권한이 없는 페이지 접근
			// Security Filter가 인터셉트하여 savedRquest에 세션 저장
			if (savedRequest != null) {
				uri = savedRequest.getRedirectUrl();
			} else if (prePage != null && !prePage.equals("")) {
				// 회원가입 -> 로그인으로 넘어온 경우 "/"로 redirect
				if (prePage.contains("/member/register")) {
					uri = "/";
				} else if (prePage.contains("/challenge/detail")) {
					uri = "/challenge/detail";
				} else {
					uri = prePage;
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
