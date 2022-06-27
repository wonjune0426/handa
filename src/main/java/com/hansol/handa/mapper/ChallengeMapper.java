package com.hansol.handa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.ChallengeVO;

@Mapper
public interface ChallengeMapper {
	
	List<ChallengeVO> selectAllChallenge();
}
