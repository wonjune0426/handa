package com.hansol.handa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hansol.handa.domain.UserVO;
import com.hansol.handa.mapper.UserMapper;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Setter(onMethod_ = {@Autowired})
	private UserMapper userMapper;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public int register(UserVO userVO) {
		
		userVO.setPassword(encoder.encode(userVO.getPassword()));
		
		return userMapper.register(userVO);
		
	}

	@Override
	public int idcheck(String member_id) {
		// 0 : 가입 가능, 1 : 가입 불가 (중복 O)
		return userMapper.read(member_id) == null ? 0 : 1;
	}

	@Override
	public UserVO read(String member_id) {
		
		return userMapper.read(member_id);
	}

	@Override
	public int amend(UserVO userVO) {
		
		return userMapper.update(userVO);
	}

	@Override
	public List<UserVO> joinMembers(int challenge_id) {

		return userMapper.joinMembers(challenge_id);
	}
}
