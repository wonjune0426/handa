package com.hansol.handa.service;

import java.util.List;

import com.hansol.handa.domain.CommentVO;

public interface CommentService {

	List<CommentVO> getComment(int challenge_id);

	void createComment(CommentVO commentVO);

	void updateComment(CommentVO commentVO);

	void deleteComment(CommentVO commentVO);

}
