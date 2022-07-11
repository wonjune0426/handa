package com.hansol.handa.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
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

	// 회원 가입
	@Override
	public int register(UserVO userVO) {
		
		userVO.setPassword(encoder.encode(userVO.getPassword()));
		
		userVO.setCertify_token(createToken());
		
		return userMapper.register(userVO);	
	}
	
	// 메일 전송
	@Override
	@Async
	public void sendMail(UserVO user) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		
		log.info("user SEND MAIL: " + user);
		
		helper.setFrom("HANDA");
		helper.setTo(user.getE_mail());
		helper.setSubject("[한다] 이메일 인증");
		helper.setText(setMailContent(user, "register"), true);
		
		javaMailSender.send(mimeMessage);
	}
	
	// 인증 메일 내용 작성
	public String setMailContent(UserVO user, String type) {
		
		StringBuffer emailContent = new StringBuffer();
		
		String token = user.getCertify_token();
		
		if (type.equals("register")) {
			emailContent.append(
					"<div style='width: 400px; height: 1000px; border-top: 4px solid #86a0e6;'>"
					+ "<p>Handa</p>"
					+ "<h1>메일 인증 안내입니다.</h1>"
					+ "<p>" + user.getMember_name() + "(" + user.getMember_id() + ")" + " 님" + "</p>"
					+ "<p>아래 <span style='color: #86a0e6;'>'메일 인증'</span> 버튼을 클릭하여 가입을 완료해주세요.</p>"
					+ "<p>감사합니다.</p>"
					+ "<br><br>"
					+ "<a href='http://localhost:8080/member/check-email-token?token=" + token + "&member_id=" + user.getMember_id() + "' target='_blank'"
					+ "		style='width:200px; height: 50px; padding: 10px 20px; color: #fff; background: #86a0e6;'>"
					+ "메일 인증"
					+ "</a>"
					+ ""
					+ "</div>");
		}
		
		if (type.equals("findPW")) {
			emailContent.append(
					"<div style='width: 400px; height: 1000px; border-top: 4px solid #86a0e6;'>"
					+ "<p>Handa</p>"
					+ "<h1>임시 비밀번호 발급 안내입니다.</h1>"
					+ "<p>" + user.getMember_name() + "(" + user.getMember_id() + ")" + " 님" + "</p>"
					+ "<p>아래의 임시 비밀번호로 로그인을 진행해주세요.</p>"
					+ "<br><br>"
					+ "<p"
					+ "		style='width:300px; height: 70px; padding: 10px 20px; "
					+ "		color: #fff; font-size: 20px; background: #86a0e6;'>"
					+ user.getPassword()
					+ "</p>"
					+ ""
					+ "</div>");
		}
		
		return emailContent.toString();
	}
	
	// 인증 메일 토큰 생성
	public String createToken() {
		String token = "";
		
		UUID uuid = UUID.randomUUID();
		
		token = uuid.toString();
		
		return token;
	}
	
	// 메일 인증 토큰 값 체크
	@Override
	public boolean checkEmailToken(String token, String member_id) {
		
		log.info("check-email-token Service: " + token + ", " + member_id);
		
		// DB에서 사용자 불러와서 파라미터로 전달받은 토큰 값과 일치하는지 확인
		UserVO user = userMapper.read(member_id);
		
		log.info("check-email-token Service UserVO: " + user);
		
		return token.equals(user.getCertify_token());
	}

	// 아이디 중복 확인
	@Override
	public int idcheck(String member_id) {
		// 0 : 가입 가능, 1 : 가입 불가 (중복 O)
		return userMapper.read(member_id) == null ? 0 : 1;
	}

	// 회원 정보 조회
	@Override
	public UserVO read(String member_id) {
		
		return userMapper.read(member_id);
	}

	
	// 회원 정보 수정
	@Override
	public int amend(UserVO userVO) {
		
		return userMapper.update(userVO);
	}

	
	// 챌린지 참여 인원 조회
	@Override
	public List<UserVO> joinMembers(int challenge_id) {

		return userMapper.joinMembers(challenge_id);
	}

	
	// 회원 권한 수정
	@Override
	public void updateAuth(UserVO vo) {
		userMapper.updateAuth(vo);
		
	}

	
	// 토큰 값 업데이트
	@Override
	public void updateToken(UserVO userVO) {
		userMapper.updateToken(userVO);
		
	}

	
	// 아이디 찾기
	@Override
	public List<UserVO> findID(UserVO vo) {
		
		List<UserVO> userList = userMapper.findID(vo);
		
		// 아이디, 이름, 메일, 가입 일자
		return userMapper.findID(vo);
	}

	@Override
	public boolean findPW(UserVO user) {
		
		// 1. 회원 테이블에 존재하는지 확인
		if (userMapper.findPW(user) != null) {
			
				
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void sendPWMail(UserVO user) throws MessagingException {
		// 2. 메일로 임시비밀번호 발급
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		
		user.setPassword(getTempPW());
		
		log.info("user SEND MAIL: " + user);
		
		helper.setFrom("HANDA");
		helper.setTo(user.getE_mail());
		helper.setSubject("[한다] 비밀번호 재발급");
		helper.setText(setMailContent(user, "findPW"), true);
		
		javaMailSender.send(mimeMessage);
		
		user.setPassword(encoder.encode(user.getPassword()));
		
		userMapper.updatePW(user);
	}
	
	// 임시 비밀번호 발급
	public String getTempPW() {
		
		int index = 0;
		
		char[] charSet = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
		};

		StringBuffer password = new StringBuffer();
		Random random = new Random();

		for (int i = 0; i < 7 ; i++) {
			double rd = random.nextDouble();
			index = (int) (charSet.length * rd);
			
			password.append(charSet[index]);
			
			System.out.println("index::" + index + "	charSet::"+ charSet[index]);
		}
		
		return password.toString(); 
	}

	@Override
	public void updatePW(UserVO vo) {
		
		vo.setPassword(encoder.encode(vo.getPassword()));
		
		userMapper.updatePW(vo);
		
	}
}
