package com.hansol.handa.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ChallengeVO {
	
	  int challenge_id; 
	  String challenge_name; 
	  String thumbnail; 
	  LocalDate startdate; 
	  LocalDate enddate; 
	  String description; 
	  LocalDate createdate;
	  String member_id; 
	  int subcategory_id;
	 
}
