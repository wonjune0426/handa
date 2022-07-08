package com.hansol.handa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansol.handa.domain.UserVO;
import com.hansol.handa.mapper.MypageMapper;

@Service
public class MypageServiceImpl implements MypageService {

	@Autowired
	private MypageMapper mapper;
	
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

}
