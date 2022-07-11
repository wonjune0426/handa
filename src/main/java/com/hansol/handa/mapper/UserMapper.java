package com.hansol.handa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.UserVO;

@Mapper
public interface UserMapper {

	UserVO read(String username);					// 회원 정보 조회
	
	int register(UserVO userVO);					// 회원 등록
	
	int update(UserVO userVO);						// 회원 정보 수정

	List<UserVO> joinMembers(int challenge_id);		// 챌린지 참여 인원 조회
	
	void updateAuth(UserVO vo);						// 회원 권한 수정
	
	void updateToken(UserVO vo);					// 토큰 값 수정
	
	List<UserVO> findID(UserVO vo);					// 아이디 찾기
	
	UserVO findPW(UserVO vo);						// 비밀번호 찾기
	
	void updatePW(UserVO vo);						// 비밀번호 변경
}
