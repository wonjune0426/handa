package com.hansol.handa.domain;

import lombok.Data;

@Data
public class CommentVO {
	private int comment_id;
	private String comment_content;
	private String createdate;
	private int challenge_id;
	private String member_id;
}
