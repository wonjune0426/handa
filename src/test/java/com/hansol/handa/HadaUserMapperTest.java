package com.hansol.handa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hansol.handa.domain.UserVO;
import com.hansol.handa.mapper.UserMapper;

@SpringBootTest
public class HadaUserMapperTest {
	
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void readUserTest() {
		
		UserVO user = mapper.read("goeunlee"); 
		
		System.out.println(user.toString());
	}

}
