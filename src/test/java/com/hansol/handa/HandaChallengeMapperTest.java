package com.hansol.handa;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hansol.handa.domain.ChallengeVO;
import com.hansol.handa.mapper.ChallengeMapper;

import ch.qos.logback.classic.Logger;

@SpringBootTest 
public class HandaChallengeMapperTest {
  
  @Autowired 
  private ChallengeMapper mapper; 
  private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
  
  @Test 
  public void selectAllChallengeTest() { 
	  List<ChallengeVO> challenge = mapper.selectAllChallenge();
  
	  logger.info(challenge.toString()); 
  } 
}
 
