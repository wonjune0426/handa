package com.hansol.handa.service;

import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
	
	@Autowired
	public JavaMailSender javaMailSender;

	@Override
	public int register(UserVO userVO) {
		
		userVO.setPassword(encoder.encode(userVO.getPassword()));
		
		return userMapper.register(userVO);	
	}
	
	@Override
	@Async
	public void sendMail(UserVO user) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		
		helper.setFrom("HANDA");
		helper.setTo(user.getE_mail());
		helper.setSubject("한다 이메일 인증");
		helper.setText(setMailContent(user), true);
		
		javaMailSender.send(mimeMessage);
	}
	
	public String setMailContent(UserVO user) {
		
		StringBuffer emailContent = new StringBuffer();
		
		emailContent.append(
				"<div style='width: 400px; height: 1000px; border-top: 4px solid #86a0e6;'>"
				+ "<small>Handa</small>"
				+ "<h1>메일인증 안내입니다.</h1>"
				+ "<p>" + user.getMember_name() + "(" + user.getMember_id() + ")" + " 님" + "</p>"
				+ "<p>아래 '메일 인증' 버튼을 클릭하여 가입을 완료해주세요.</p>"
				+ "<p>감사합니다.</p>"
				+ ""
				+ "<a href='http://localhost:8080/member/check-email-token?member_id=" + user.getMember_id() + "' target='_blank'"
				+ "		style='width:200px; height: 50px; padding: 10px 20px; color: #fff; background: #86a0e6;'>"
				+ "메일 인증"
				+ "</a>"
				+ ""
				+ "</div>");
		
		return emailContent.toString();
	}
	
	public String getCertifiedKey() {
		return "";
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

	@Override
	public void updateAuth(UserVO vo) {
		userMapper.updateAuth(vo);
		
	}
}
