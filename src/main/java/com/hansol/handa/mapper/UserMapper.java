package com.hansol.handa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.UserVO;

@Mapper
public interface UserMapper {

	UserVO read(String username); 
	
	int register(UserVO userVO);
	
	int update(UserVO userVO);

	List<UserVO> joinMembers(int challenge_id);
}
