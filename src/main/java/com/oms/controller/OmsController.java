package com.oms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oms.config.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Front End Design Templates를 관리하는 컨트롤러
 * @author swjang128
 *
 */
@Controller
@RequestMapping("oms")
@Slf4j
public class OmsController {
	@Autowired
	CommonUtil commonUtil;
	
	/**
	 * 메인페이지(로그인 페이지)
	 * @param model
	 * @return
	 */
	@GetMapping("")
  	public String main(Model model, @RequestParam(value="result", required=false) String result) {
		String message = "";
		log.info("**** result: {}", result);
		if (result != null) {
			switch(result) {
				case "notFoundOrBadCredentials" -> message = "이메일 또는 비밀번호를 확인해주세요";
				case "accountExpired" -> message = "만료된 계정입니다. 비밀번호를 변경해주세요";
				case "credentialsExpired" -> message = "만료된 계정입니다. 비밀번호를 변경해주세요";
				case "disabled" -> message = "휴면 계정입니다";
				case "blocked" -> message = "계정이 잠겨있습니다. 비밀번호를 초기화해주세요";
				default -> message = "로그인 중 에러가 발생하였습니다";
			}
		}
		
		log.info("**** message: {}", message);
		model.addAttribute("message", message);
		
  		return "login";
  	}

	/**
	 * Account
	 * @param model
	 * @return
	 */
	@GetMapping("account")
  	public String member(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "account";
  	}
	
	/**
	 * Project
	 * @param model
	 * @return
	 */
	@GetMapping("project")
  	public String project(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "project";
  	}
	
	/**
	 * Dashboard
	 * @param model
	 * @return
	 */
	@GetMapping("dashboard")
  	public String dashboard(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "dashboard";
  	}
	
	/**
	 * Task(Kanban)
	 * @param model
	 * @return
	 */
	@GetMapping("task")
  	public String task(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "task";
  	}
	
}
