package com.hansol.handa.domain;

import lombok.Data;

@Data
public class ChallengeVO {
	
	 private int challenge_id; 				// 챌린지 아이디
	 private String challenge_name; 		// 챌린지 이름
	 private String thumbnail; 				// 썸네일
	 private String startdate; 				// 시작 날짜 (0000-00-00)
	 private String enddate; 				// 종료 날짜 (0000-00-00)
	 private String description; 			// 설명
	 private String createdate;				// 생성 날짜 (0000-00-00 00:00:00)
	 private String member_id; 				// 생성자 아이디
	 private int subcategory_id;			// 소 카테고리 아이디
	 private String challenge_type;			// 챌린지 타입 
	 private String challenge_state;		// 챌린지 상태 
	 private String challenge_cost;			// 챌린지 가격
	 private int dday;						// 디데이 날짜
	 private int limit_member;				// 챌린지 모집 인원
	 
	 private String subcategory_name;		// 소 카테고리 이름
	 private String maincategory_name;		// 대 카테고리 이름
	 
	 private int count;						// 참여 인원
	 private JoinVO joinVO;					// 참여자 목록 (인원 수)
}
