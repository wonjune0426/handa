package com.hansol.handa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hansol.handa.domain.ChallengeVO;

public interface ChallengeService {
	
	/* 챌린지 리스트 조회 */
	public List<ChallengeVO> selectChallegeList(String category, String sortType, String createdate, String count, String searchWord, String challengeType);
	
	public Map<String, String> selectCategoryName(int categoryID);		// 카테고리 이름 조회
	public int selectCount(HashMap<String, Object> countMap);			// 챌린지 개수 조회
	
	public List<ChallengeVO> selectPopular();							// 메인 페이지 인기 챌린지 조회
	public List<ChallengeVO> selectCost();								// 메인 페이지 유료 챌린지 조회
	/* *************** */
	
	public List<String> getimagelist(String searchWord);
	public void createChallenge(ChallengeVO challengeVO);
	public void joinChallenge(String member_id,Integer challenge_id);
	public ChallengeVO getChallenge(int challenge_id);
	public void updateChallenge(ChallengeVO challengeVO);
	public void deleteChallenge(ChallengeVO challengeVO);
	public void secessionChallenge(ChallengeVO challengeVO);
	public ChallengeVO detailChallenge(int challenge_id);
}
