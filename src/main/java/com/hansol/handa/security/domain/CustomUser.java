package com.hansol.handa.security.domain;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.hansol.handa.domain.UserVO;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CustomUser extends User {

	private static final long serialVersionUID = 1L;

	private UserVO user;

	private String testParam;

	/*
	 * public CustomUser(String username, String password, Collection<? extends
	 * GrantedAuthority> authorities, UserVO vo) { super(username, password,
	 * authorities); this.user = vo; }
	 */

	public CustomUser(UserVO vo) {

		super(vo.getMember_id(), vo.getPassword(), vo.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));

		this.user = vo;

		this.testParam = "test";

		log.info("CustomUSer - " + vo.toString());
	}

}
