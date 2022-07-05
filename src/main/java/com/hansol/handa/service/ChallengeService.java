package com.hansol.handa.service;

import java.util.List;
import java.util.Map;

import com.hansol.handa.domain.ChallengeVO;

public interface ChallengeService {
	
	/* 챌린지 리스트 조회 */
	public List<ChallengeVO> selectChallegeList(String category, String sortType, String createdate, String count);
	public Map<String, String> selectCategoryName(int categoryID);		// 카테고리 이름 조회
	/* *************** */
	
	public List<String> getimagelist(String searchWord);
	public void createChallenge(ChallengeVO challengeVO);
	public void joinChallenge(String member_id,Integer challenge_id);
	public ChallengeVO getChallenge(int challenge_id);
	public void updateChallenge(ChallengeVO challengeVO);
}
