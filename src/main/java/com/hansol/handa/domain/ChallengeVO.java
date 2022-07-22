package com.hansol.handa.domain;


import lombok.Data;

@Data
public class ChallengeVO {
	
	 private int challenge_id; 
	 private String challenge_name; 
	 private String thumbnail; 
	 private String startdate; 
	 private String enddate; 
	 private String description; 
	 private String createdate;
	 private String member_id; 
	 private int subcategory_id;
	 private int limit_member;
	 private String challenge_type;
	 private String challenge_state;
	 private String challenge_cost;
	 private int dday;
	 
	 private String subcategory_name;
	 private String maincategory_name;
	 
	 private int count;
	 private JoinVO joinVO;
}
