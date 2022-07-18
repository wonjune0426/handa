package com.hansol.handa.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hansol.handa.domain.ChallengeVO;

@Mapper
public interface ChallengeMapper {
	
	/* 챌린지 리스트 조회 */
	List<ChallengeVO> selectChallengeList(Map<String, Object> map);				// 전체 리스트 조회
	List<ChallengeVO> selectChallegeListCategory(Map<String, Object> map);		// 카테고리 별 리스트 조회
	List<ChallengeVO> selectChallegeListMainCategory(Map<String, Object> map);	// 대 카테고리 별 리스트 조회
	
	Map<String, String> selectCategoryName(int sub_category_id);				// 카테고리 이름 조회
	int selectCount(HashMap<String, Object> countMap);							// 챌린지 개수 조회
	
	List<ChallengeVO> selectPopular();											// 메인 페이지 인기 챌린지 조회
	List<ChallengeVO> selectCost();												// 메인 페이지 유료 챌린지 조회
	/* *************** */
	
	void createChallenge(ChallengeVO challengeVO);
	void joinChallenge(@Param("member_id")String member_id,@Param("challenge_id") Integer challenge_id);
	ChallengeVO getChallenge(int challenge_id);
	void updateChallenge(ChallengeVO challengeVO);
	void deleteChallenge(ChallengeVO challengeVO);
	void secessionChallenge(ChallengeVO challengeVO);
	ChallengeVO detailChallenge(int challenge_id);

}
