package com.oms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	 * 메인페이지(로그인)
	 * @param model
	 * @return
	 */
	@GetMapping("")
  	public String main(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "login";
  	}
	
	/**
	 * Member
	 * @param model
	 * @return
	 */
	@GetMapping("member")
  	public String member(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "member";
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
