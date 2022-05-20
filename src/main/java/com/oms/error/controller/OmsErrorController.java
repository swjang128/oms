package com.oms.error.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oms.config.ResponseManager;

import io.micrometer.core.ipc.http.HttpSender.Request;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OmsErrorController implements ErrorController{
	/**
	 * 에러 발생시 처리하는 컨트롤러 
	 * @param request
	 * @return status에 맞는 템플릿의 에러페이지 호출
	 */
	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
		String status = Integer.toString((int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		status = "_"+status;
		model.addAttribute("result", ResponseManager.valueOf(status));
		return "error";
	}

}
