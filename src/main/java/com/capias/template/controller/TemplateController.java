package com.capias.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capias.config.CommonUtil;

/**
 * Front End Design Templates를 관리하는 컨트롤러
 * @author swjang128
 *
 */
@Controller
@RequestMapping("template")
public class TemplateController {
	@Autowired
	CommonUtil commonUtil;
	
	/**
	 * Table Sample
	 * @param model
	 * @return
	 */
	@GetMapping("tables")
  	public String tables(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "tables";
  	}
	
	/**
	 * Button Sample
	 * @param model
	 * @return
	 */
	@GetMapping("buttons")
  	public String buttons(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "buttons";
  	}
	
	/**
	 * Card Sample
	 * @param model
	 * @return
	 */
	@GetMapping("cards")
  	public String cards(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "cards";
  	}
	
}
