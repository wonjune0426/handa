package com.hansol.handa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansol.handa.domain.CommentVO;
import com.hansol.handa.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public List<CommentVO> getComment(int challenge_id) {
		return commentMapper.getComment(challenge_id);
	}

	@Override
	public void createComment(CommentVO commentVO) {
		commentMapper.createComment(commentVO);
	}

	@Override
	public void updateComment(CommentVO commentVO) {
		commentMapper.updateComment(commentVO);
	}

	@Override
	public void deleteComment(CommentVO commentVO) {
		commentMapper.deleteComment(commentVO);
	}

}
