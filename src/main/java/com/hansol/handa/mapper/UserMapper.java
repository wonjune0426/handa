package com.hansol.handa.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.UserVO;

@Mapper
public interface UserMapper {

	UserVO read(String username); 
	
	int register(UserVO userVO);
}
