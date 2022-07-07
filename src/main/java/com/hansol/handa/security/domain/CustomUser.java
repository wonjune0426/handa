package com.hansol.handa.security.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
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

	public CustomUser(UserVO vo, Collection<? extends GrantedAuthority> authorities) {

		super(vo.getMember_id(), vo.getPassword(), authorities);

		this.user = vo;

		log.info("CustomUSer - " + vo.toString());
	}
}
