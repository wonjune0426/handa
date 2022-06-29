package com.hansol.handa.service;

import com.hansol.handa.domain.UserVO;

public interface UserService {
	
	int register(UserVO userVO);
	
	int idcheck(String member_id);
}
