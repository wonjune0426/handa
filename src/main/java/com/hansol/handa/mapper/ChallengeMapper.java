package com.hansol.handa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.ChallengeVO;

@Mapper
public interface ChallengeMapper {
	

	/* 챌린지 리스트 조회 */
	List<ChallengeVO> selectAllChallenge();							// 전체 리스트 조회 (최신순)
	List<ChallengeVO> selectAllChallengeDesc();						// 전체 리스트 조회 (오래된 순)
	List<ChallengeVO> selectAllChallengeJoin();						// 전체 리스트 조회 (참여인원 순)
	
	List<ChallengeVO> selectChallengeList(int category_id);			// 카테고리 별 리스트 조회 (최신순)
	List<ChallengeVO> selectChallengeListDesc(int category_id);		// 카테고리 별 리스트 조회 (오래된 순)
	List<ChallengeVO> selectChallengeListJoin(int category_id);		// 카테고리 별 리스트 조회 (참여인원 순)
	
	Map<String, String> selectCategoryName(int sub_category_id);	// 카테고리 이름 조회

	void createChallenge(ChallengeVO challengeVO);

}
