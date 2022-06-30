package com.hansol.handa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.ChallengeVO;

@Mapper
public interface ChallengeMapper {
	
	List<ChallengeVO> selectAllChallenge();
	List<ChallengeVO> selectAllChallengeDesc();
	List<ChallengeVO> selectChallengeList(int category_id);
	List<ChallengeVO> selectChallengeListDesc(int category_id);
	Map<String, String> selectCategoryName(int sub_category_id);
	void createChallenge(ChallengeVO challengeVO);
}
