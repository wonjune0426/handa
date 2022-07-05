package com.hansol.handa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansol.handa.domain.CommentVO;
import com.hansol.handa.domain.UserVO;
import com.hansol.handa.service.CommentService;
import com.hansol.handa.service.UserService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
//댓글 조회
	@GetMapping("/comment")
	public @ResponseBody Map<String,Object> getComment(@RequestParam(required = true) int challenge_id) {
		Map<String,Object> returnMap=new HashMap<>();
		List<CommentVO> commentList=commentService.getComment(challenge_id);
		List<UserVO> memberList=new ArrayList<>();
		for(CommentVO c:commentList) {
			memberList.add(userService.read(c.getMember_id()));
		}
		returnMap.put("commentList", commentList);
		returnMap.put("memberList", memberList);
		
		return returnMap;
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
