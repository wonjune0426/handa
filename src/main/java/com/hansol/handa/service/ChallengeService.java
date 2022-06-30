package com.hansol.handa.service;

import java.util.List;
import java.util.Map;

import com.hansol.handa.domain.ChallengeVO;

public interface ChallengeService {
	
	/* 챌린지 리스트 조회 */
	public List<ChallengeVO> selectAllChallenge();							// 전체 리스트 조회 (최신순)
	public List<ChallengeVO> selectAllChallengeDesc();						// 전체 리스트 조회 (오래된 순)
	public List<ChallengeVO> selectAllChallengeJoin();						// 전체 리스트 조회 (참여인원 순)
	
	public List<ChallengeVO> selectChallengeList(int category_id);			// 카테고리 별 리스트 조회 (최신순)
	public List<ChallengeVO> selectChallengeListDesc(int category_id);		// 카테고리 별 리스트 조회 (오래된 순)
	public List<ChallengeVO> selectChallengeListJoin(int category_id);		// 카테고리 별 리스트 조회 (참여인원 순)
	
	public Map<String, String> selectCategoryName(int sub_category_id);		// 카테고리 이름 조회
	/* *************** */
	
	public List<String> getimagelist(String searchWord);
	public void createChallenge(ChallengeVO challengeVO);
}
