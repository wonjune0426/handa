package com.hansol.handa.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebErrorController implements ErrorController {

	@GetMapping("/error") 
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				
				model.addAttribute("code", HttpStatus.NOT_FOUND.value());
				model.addAttribute("msg", "URL을 확인해주세요.");
				
				return "error/error";
			}

			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				
				model.addAttribute("code", HttpStatus.FORBIDDEN.value());
				model.addAttribute("msg", "허용되지 않는 접근입니다.");
				
				return "error/error";
			}
		}
		
		model.addAttribute("code", "ERROR");
		model.addAttribute("msg", "오류");

		return "error/error";
	}

}
