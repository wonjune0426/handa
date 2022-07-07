package com.hansol.handa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@SpringBootTest
public class MailTest {
	
	@Autowired
	public JavaMailSender javaMailSender;

	@Async
	public void sendMail(String email) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		// simpleMessage.setFrom("보낸사람@naver.com"); // NAVER, DAUM, NATE일 경우 넣어줘야 함
		simpleMessage.setTo(email);
		simpleMessage.setSubject("이메일 인증");
		simpleMessage.setText("인증번호: 123456");
		javaMailSender.send(simpleMessage);
	}
	
	@Test
	public void sendMailTest() {
		sendMail("lge_97@naver.com");
	}
}
