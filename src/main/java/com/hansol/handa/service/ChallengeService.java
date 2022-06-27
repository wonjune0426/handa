package com.hansol.handa.service;

import java.util.List;

import com.hansol.handa.domain.ChallengeVO;

public interface ChallengeService {
	public List<ChallengeVO> selectAllChallenge() throws Exception;
}
