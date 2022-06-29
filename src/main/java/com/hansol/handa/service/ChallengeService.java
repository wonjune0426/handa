package com.hansol.handa.service;

import java.util.List;
import java.util.Map;

import com.hansol.handa.domain.ChallengeVO;

public interface ChallengeService {
	public List<ChallengeVO> selectAllChallenge();
	public List<ChallengeVO> selectAllChallengeDesc();
	public List<ChallengeVO> selectChallengeList(int category_id);
	public List<ChallengeVO> selectChallengeListDesc(int category_id);
	public Map<String, String> selectCategoryName(int sub_category_id);
	public List<String> getimagelist(String searchWord);
}
