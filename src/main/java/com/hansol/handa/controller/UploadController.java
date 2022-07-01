package com.hansol.handa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UploadController {
	
	@PostMapping("/upload")
	public String  uploadFile(HttpServletRequest request) {
		
		log.info("upload file --- " + request.getParameter("itemName"));
		
		return "member/sample";
	}
	

}
