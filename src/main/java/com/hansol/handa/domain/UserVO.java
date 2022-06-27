package com.hansol.handa.domain;

import lombok.Data;

@Data
public class UserVO {

	String member_id;
	String password;
	String member_name;
	String member_call;
	String e_mail;
	String gender;
	int affiliates_id;
	String profile_name;
	String profile_path;
	String profile_type;
	String createdate;
	String position_id;
	
}
