package com.hansol.handa.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.UserVO;

@Mapper
public interface MypageMapper {
	UserVO selectMemberInfo(String member_id);		// 내 정보 조회
	int selectProduceCount(String member_id);		// 생성 챌린지 개수 조회
	int selectPartCount(String member_id);			// 참여 챌린지 개수 조회
}
