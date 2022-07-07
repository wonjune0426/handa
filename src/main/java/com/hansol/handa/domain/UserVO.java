package com.hansol.handa.domain;

import lombok.Data;

@Data
public class UserVO {

	private String member_id;
	private String password;
	private String member_name;
	private String member_call;
	private String e_mail;
	private String gender;
	private int affiliates_id;
	private String profile_name;
	private String profile_uuid;
	private String profile_path;
	private String profile_type;
	private String createdate;
	private int position_id;
	private String auth;
	private String certifyToken;
}
