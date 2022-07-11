package com.hansol.handa.service;

import java.util.List;

import javax.mail.MessagingException;

import com.hansol.handa.domain.UserVO;

public interface UserService {
	
	int register(UserVO userVO);								// 회원 가입
	
	int idcheck(String member_id);								// 아이디 중복 확인
	
	UserVO read(String member_id);								// 회원 정보 조회
	
	int amend(UserVO userVO);									// 회원 정보 수정
	
	List<UserVO> joinMembers(int challenge_id);					// 챌린지 참여인원 조회
	
	void sendMail(UserVO user) throws MessagingException;		// 인증 메일 전송
	
	void updateAuth(UserVO vo);									// 회원 권한 수정
	
	boolean checkEmailToken(String token, String member_id);	// 메일 인증 토큰값 확인
	
	void updateToken(UserVO userVO);							// 토큰 값 업데이트
	
	List<UserVO> findID(UserVO vo);								// 아이디 찾기
	
	boolean findPW(UserVO vo);									// 비밀번호 찾기
	
	void sendPWMail(UserVO user) throws MessagingException;		// 비밀번호 재발급 메일 
	
}
