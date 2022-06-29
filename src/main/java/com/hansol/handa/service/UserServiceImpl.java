package com.hansol.handa.service;

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
}
