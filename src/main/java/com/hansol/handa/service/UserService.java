package com.hansol.handa.service;

import java.util.List;

import javax.mail.MessagingException;

import com.hansol.handa.domain.UserVO;

public interface UserService {
	
	int register(UserVO userVO);
	
	int idcheck(String member_id);
	
	UserVO read(String member_id);
	
	int amend(UserVO userVO);
	
	List<UserVO> joinMembers(int challenge_id);
	
	void sendMail(UserVO user) throws MessagingException;
	
	void updateAuth(UserVO vo);
	
	boolean checkEmailToken(String token, String member_id);
	
	void updateToken(UserVO userVO);
}
