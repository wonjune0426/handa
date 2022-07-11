package com.hansol.handa.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansol.handa.domain.ChallengeVO;
import com.hansol.handa.domain.UserVO;
import com.hansol.handa.mapper.MypageMapper;

import ch.qos.logback.classic.Logger;

@Service
public class MypageServiceImpl implements MypageService {

	@Autowired
	private MypageMapper mapper;
	@Autowired
	private ChallengeServiceImpl challengeService;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserVO selectMemberInfo(String member_id) {
		return mapper.selectMemberInfo(member_id);
	}

	@Override
	public int selectProduceCount(String member_id) {
		return mapper.selectProduceCount(member_id);
	}

	@Override
	public int selectPartCount(String member_id) {
		return mapper.selectPartCount(member_id);
	}

	@Override
	public List<ChallengeVO> selectProdeceLimit(String member_id) {
		List<ChallengeVO> list = mapper.selectProdeceLimit(member_id);
		list = challengeService.getChallengeState(list);
		
		for(ChallengeVO challenge : list) {
			String[] date = challenge.getCreatedate().split(" ");
			challenge.setCreatedate(date[0]);
		}
		
		return list;
	}

	@Override
	public List<ChallengeVO> selectPartLimit(String member_id) {
		List<ChallengeVO> list = mapper.selectPartLimit(member_id);
		list = challengeService.getChallengeState(list);
		
		for(ChallengeVO challenge : list){
			String[] date = challenge.getCreatedate().split(" ");
			challenge.setCreatedate(date[0]);
		}
		
		return list;
	}

}
