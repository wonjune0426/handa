package com.hansol.handa.service;

import java.util.HashMap;
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
	
	// 내 정보 조회
	@Override
	public UserVO selectMemberInfo(String member_id) {
		return mapper.selectMemberInfo(member_id);
	}

	// 생성 챌린지 개수
	@Override
	public int selectProduceCount(String member_id) {
		return mapper.selectProduceCount(member_id);
	}

	// 참여 챌린지 개수
	@Override
	public int selectPartCount(String member_id) {
		return mapper.selectPartCount(member_id);
	}

	// 생성 챌린지 조회 (3개)
	@Override
	public List<ChallengeVO> selectProdeceLimit(String member_id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("member_id", member_id);
		map.put("mypage", true);
		
		List<ChallengeVO> list = mapper.selectProdece(map);
		list = challengeService.getChallengeState(list);
		list = challengeService.amendList(list);
		
		return list;
	}

	// 참여 챌린지 조회 (3개)
	@Override
	public List<ChallengeVO> selectPartLimit(String member_id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("member_id", member_id);
		map.put("mypage", true);
		
		List<ChallengeVO> list = mapper.selectPart(map);
		list = challengeService.getChallengeState(list);
		list = challengeService.amendList(list);
		
		return list;
	}

	// 생성 챌린지 리스트
	@Override
	public List<ChallengeVO> selectProduce(String member_id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("member_id", member_id);
		map.put("mypage", false);
		
		List<ChallengeVO> list = mapper.selectProdece(map);
		list = challengeService.getChallengeState(list);
		list = challengeService.amendList(list);
		
		return list;
	}

	// 참여 챌린지 리스트
	@Override
	public List<ChallengeVO> selectPart(String member_id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("member_id", member_id);
		map.put("mypage", false);
		
		List<ChallengeVO> list = mapper.selectPart(map);
		list = challengeService.getChallengeState(list);
		list = challengeService.amendList(list);
		
		return list;
	}

}
