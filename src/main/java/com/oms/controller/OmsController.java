package com.oms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oms.config.CommonUtil;
import com.oms.dto.AccountDTO;
import com.oms.dto.LawDTO;
import com.oms.entity.Account;
import com.oms.service.AccountService;

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
	@Autowired
	AccountService accountService;
	
	/**
	 * 메인페이지(로그인 페이지)
	 * @param model
	 * @return
	 */
	@GetMapping("")
  	public String main(Model model, @RequestParam(value="result", required=false) String result) {
		String message = "";
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
		model.addAttribute("message", message);
  		return "login";
  	}

	/**
	 * Account
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("account")
  	public String member(Model model) {		
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
		// 파라미터, 결과값 변수 선언		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		LocalDate localDate = LocalDate.now();
		
		// 전체 계정 목록 + 개수		
		resultMap = accountService.read(paramMap, resultMap);
		Long totalAccount = (long) resultMap.size();		
		model.addAttribute("accountList", resultMap.get("accountList"));		
		model.addAttribute("totalAccount", totalAccount);
		
		// 1달전 입사한 계정 개수
		Long monthAgoAccount = (long) 0;
		resultMap.clear();
		paramMap.put("endDate", localDate.minusMonths(1));
		resultMap = accountService.count(paramMap, resultMap);
		if (resultMap != null) {
			monthAgoAccount = (Long) resultMap.get("count");
		}
		model.addAttribute("monthAgoAccount", monthAgoAccount);
		
		// 전체 계정 개수 대비 1달전 계정 개수
		Long compareAccount = (long) 100;
		if (monthAgoAccount > 0) {
			compareAccount = (totalAccount / monthAgoAccount) * 100;
		}
		model.addAttribute("compareAccount", compareAccount);

		// ACTIVE 계정 개수
		paramMap.clear();
		resultMap.clear();
		Long activeAccount = (long) 0;
		paramMap.put("status", Account.Status.ACTIVE);
		resultMap = accountService.count(paramMap,  resultMap);
		activeAccount = (Long) resultMap.get("count");
		double compareActiveAccount = ((double) activeAccount / (double) totalAccount) * 100;
		compareActiveAccount = Math.round(compareActiveAccount * 100) / 100.0;
		model.addAttribute("activeAccount", activeAccount);
		model.addAttribute("compareActiveAccount", compareActiveAccount);
		
		// EXPIRED 계정 개수
		paramMap.clear();
		resultMap.clear();
		Long expiredAccount = (long) 0;
		paramMap.put("status", Account.Status.EXPIRED);
		resultMap = accountService.count(paramMap,  resultMap);
		expiredAccount = (Long) resultMap.get("count");
		double compareExpiredAccount = ((double) expiredAccount / (double) totalAccount) * 100;
		compareExpiredAccount = Math.round(compareExpiredAccount * 100) / 100.0;
		model.addAttribute("expiredAccount", expiredAccount);
		model.addAttribute("compareExpiredAccount", compareExpiredAccount);
		
		// BLOCKED 계정 개수
		paramMap.clear();
		resultMap.clear();
		Long blockedAccount = (long) 0;
		paramMap.put("status", Account.Status.BLOCKED);
		resultMap = accountService.count(paramMap,  resultMap);
		blockedAccount = (Long) resultMap.get("count");
		double compareBlockedAccount = ((double) blockedAccount / (double) totalAccount) * 100;
		compareBlockedAccount = Math.round(compareBlockedAccount * 100) / 100.0;
		model.addAttribute("blockedAccount", blockedAccount);
		model.addAttribute("compareBlockedAccount", compareBlockedAccount);
		
		// account 페이지로 이동
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
	 * DashBoard
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
	 * Task(KanBan)
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
