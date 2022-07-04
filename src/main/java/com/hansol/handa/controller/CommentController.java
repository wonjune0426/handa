package com.hansol.handa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansol.handa.domain.CommentVO;
import com.hansol.handa.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/comment")
	public @ResponseBody void getComment(@RequestParam(required = true) int challenge_id,Model model) {
		model.addAttribute("comments",commentService.getComment(challenge_id));
	}
	
// 댓글 작성
	@PostMapping("/comment")
	public @ResponseBody void createComment(CommentVO commentVO) {
		commentService.createComment(commentVO);
	}
	
// 댓글 수정
	@PostMapping("/comment-amend")
	public @ResponseBody void updateComment(CommentVO commentVO) {
		commentService.updateComment(commentVO);
	}
	
// 댓글 삭제
	@PostMapping("/comment-remove")
	public @ResponseBody void deleteComment(CommentVO commentVO) {
		commentService.deleteComment(commentVO);
	}
}
