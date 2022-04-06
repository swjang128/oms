package com.oms.error.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OmsErrorController implements ErrorController{
	/**
	 * 에러 발생시 처리하는 컨트롤러 
	 * @param request
	 * @return status에 맞는 템플릿의 에러페이지 호출
	 */
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		return "/error/"+status;
	}

}
