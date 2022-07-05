package com.hansol.handa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hansol.handa.domain.CommentVO;

@Mapper
public interface CommentMapper {

	List<CommentVO> getComment(int challenge_id);

	void createComment(CommentVO commentVO);

	void updateComment(CommentVO commentVO);

	void deleteComment(CommentVO commentVO);

}
