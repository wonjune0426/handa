package com.hansol.handa.service;

import java.util.List;

import com.hansol.handa.domain.ChallengeVO;
import com.hansol.handa.domain.UserVO;

public interface MypageService {
	UserVO selectMemberInfo(String member_id);					// 내 정보 조회
	int selectProduceCount(String member_id);					// 생성 챌린지 개수 조회
	int selectPartCount(String member_id);						// 참여 챌린지 개수 조회
	List<ChallengeVO> selectProdeceLimit(String member_id);		// 생성 챌린지 조회 (3개)
	List<ChallengeVO> selectPartLimit(String member_id);		// 참여 챌린지 조회 (3개)
}
