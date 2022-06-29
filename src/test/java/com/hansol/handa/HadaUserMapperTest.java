package com.hansol.handa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hansol.handa.domain.UserVO;
import com.hansol.handa.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class HadaUserMapperTest {
	
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void readUserTest() {
		
		UserVO user = mapper.read("goeunlee"); 
		
		System.out.println(user.toString());
	}
	
	@Test
	public void insertUserTest() {
		
		UserVO vo = new UserVO();
		
		vo.setMember_id("test6");
		vo.setPassword("$2a$10$h105Nqwm6h/DHqhG6auwhuwyoiZ13VZ00ib50XBMjms6kBe9xX/Pe");
		vo.setMember_name("테스트유저2");
		vo.setMember_call("010-1111-1111");
		vo.setE_mail("test2@hansol.com");
		vo.setGender("남");
		vo.setAffiliates_id(2);
		vo.setPosition_id(3);
		
		int result = mapper.register(vo);
		
		log.info("------------------test");
		log.info("insert test" + result);
		
	}

}
