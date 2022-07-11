package com.hansol.handa.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.ChallengeVO;
import com.hansol.handa.domain.UserVO;

@Mapper
public interface MypageMapper {
	UserVO selectMemberInfo(String member_id);								// 내 정보 조회
	int selectProduceCount(String member_id);								// 생성 챌린지 개수 조회
	int selectPartCount(String member_id);									// 참여 챌린지 개수 조회
	
	List<ChallengeVO> selectProdece(HashMap<String, Object> map);			// 생성 챌린지 조회 
	List<ChallengeVO> selectPart(HashMap<String, Object> map); 				// 참여 챌린지 조회 
}
