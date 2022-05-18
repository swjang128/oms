package com.oms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oms.config.CommonInterceptor;
import com.oms.config.CommonUtil;
import com.oms.dto.AccountDTO;
import com.oms.dto.LawDTO;
import com.oms.entity.Account;
import com.oms.entity.Department;
import com.oms.entity.Menu.UseYn;
import com.oms.entity.Position;
import com.oms.service.AccountService;
import com.oms.service.DepartmentService;
import com.oms.service.MenuService;
import com.oms.service.PositionService;
import com.oms.service.TeamService;

import lombok.extern.slf4j.Slf4j;

/**
 * 웹 페이지 컨트롤러
 * @author JSW
 *
 */
@Controller
@RequestMapping("oms")
@Slf4j
public class OmsController {
	@Autowired
	CommonInterceptor interceptor;
	@Autowired
	AccountService accountService;
	@Autowired
	TeamService teamService;
	@Autowired
	MenuService menuService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	PositionService positionService;
	
	/**
	 * 메인페이지(로그인 페이지)
	 * @param model, HttpServletRequest
	 * @return
	 */
	@GetMapping("")
  	public String main(Model model, HttpServletRequest request, @RequestParam(value="result", required=false) String result) {
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
	 * @param model, HttpServletRequest
	 * @return String
	 * @throws Exception 
	 */
	@GetMapping("account")
  	public String account(Model model, HttpServletRequest request) throws Exception {
		// 현재 서비스 위치 Set
		model.addAttribute("url", request.getAttribute("url"));
		model.addAttribute("menuName", request.getAttribute("menuName"));
		
		// 파라미터, 결과값 변수 선언		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// 전체 계정 목록 + 개수
		paramMap.clear();
		resultMap.clear();
		resultMap = accountService.read(paramMap, resultMap);
		Long totalAccount = (long) resultMap.size();		
		model.addAttribute("accountList", resultMap.get("accountList"));		
		model.addAttribute("totalAccount", totalAccount);
		
		// 1달전 입사한 계정 개수
		Long monthAgoAccount = (long) 0;
		resultMap.clear();
		paramMap.put("endDate", LocalDate.now().minusMonths(1));
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
		
		// 부서 조회
		paramMap.clear();
		resultMap.clear();
		resultMap = departmentService.read(paramMap, resultMap);
		model.addAttribute("departmentList", resultMap.get("departmentList"));
		// 부서 개수 조회
		Long useDepartment = (long) 0;
		Long unUseDepartment = (long) 0;
		Long totalDepartment = (long) 0;		
		// 부서(전체) 개수
		paramMap.clear();
		resultMap.clear();
		resultMap = departmentService.count(paramMap, resultMap);
		totalDepartment = (Long) resultMap.get("count");
		model.addAttribute("totalDepartment", totalDepartment);
		// 부서(사용) 개수
		resultMap.clear();
		paramMap.put("useYn", Department.UseYn.Y);
		resultMap = departmentService.count(paramMap, resultMap);
		useDepartment = (Long) resultMap.get("count");
		model.addAttribute("useDepartment", useDepartment);
		// 부서(미사용) 개수
		paramMap.clear();
		resultMap.clear();
		paramMap.put("useYn", Department.UseYn.N);
		resultMap = departmentService.count(paramMap, resultMap);
		unUseDepartment = (Long) resultMap.get("count");
		model.addAttribute("unUseDepartment", unUseDepartment);
		// 부서(사용/미사용 비율) 세팅
		double useDepartmentRate = ((double) useDepartment / (double) totalDepartment) * 100;
		useDepartmentRate = Math.round(useDepartmentRate * 100) / 100.0;
		model.addAttribute("useDepartmentRate", useDepartmentRate);
		model.addAttribute("unUseDepartmentRate", (double) 100 - useDepartmentRate);
		
		// 직급 조회
		paramMap.clear();
		resultMap.clear();
		resultMap = positionService.read(paramMap, resultMap);
		model.addAttribute("positionList", resultMap.get("positionList"));
		// 직급 개수 조회
		Long usePosition = (long) 0;
		Long unUsePosition = (long) 0;
		Long totalPosition = (long) 0;		
		// 직급(전체) 개수
		paramMap.clear();
		resultMap.clear();
		resultMap = positionService.count(paramMap, resultMap);
		totalPosition = (Long) resultMap.get("count");
		model.addAttribute("totalPosition", totalPosition);
		// 직급(사용) 개수
		resultMap.clear();
		paramMap.put("useYn", Position.UseYn.Y);
		resultMap = positionService.count(paramMap, resultMap);
		usePosition = (Long) resultMap.get("count");
		model.addAttribute("usePosition", usePosition);
		// 직급(미사용) 개수
		paramMap.clear();
		resultMap.clear();
		paramMap.put("useYn", Position.UseYn.N);
		resultMap = positionService.count(paramMap, resultMap);
		unUsePosition = (Long) resultMap.get("count");
		model.addAttribute("unUsePosition", unUsePosition);
		// 직급(사용/미사용 비율) 세팅
		double usePositionRate = ((double) usePosition / (double) totalPosition) * 100;
		usePositionRate = Math.round(usePositionRate * 100) / 100.0;
		model.addAttribute("usePositionRate", usePositionRate);
		model.addAttribute("unUsePositionRate", (double) 100 - usePositionRate);
		
		// account 페이지로 이동
  		return "account";
  	}
	
	/**
	 * Department
	 * @param model, HttpServletRequest
	 * @return String
	 */
	@GetMapping("department")
  	public String department(Model model, HttpServletRequest request) {
		// 현재 서비스 위치 Set
		model.addAttribute("locationUrl", request.getAttribute("locationUrl"));
		model.addAttribute("locationName", request.getAttribute("locationName"));
		model.addAttribute("parentLocationName", request.getAttribute("parentLocationName"));
		
		// 파라미터, 결과값 변수 선언		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 팀 목록 조회
		resultMap = teamService.read(resultMap);
		model.addAttribute("departmentList", resultMap.get("departmentList"));		
		
  		return "department";
  	}
	
	/**
	 * Team
	 * @param model, HttpServletRequest
	 * @return String
	 */
	@GetMapping("team")
  	public String team(Model model, HttpServletRequest request) {
		// 현재 서비스 위치 Set
		model.addAttribute("locationUrl", request.getAttribute("locationUrl"));
		model.addAttribute("locationName", request.getAttribute("locationName"));
		model.addAttribute("parentLocationName", request.getAttribute("parentLocationName"));
		
		// 파라미터, 결과값 변수 선언		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 팀 목록 조회
		resultMap = teamService.read(resultMap);
		model.addAttribute("teamList", resultMap.get("teamList"));		
		
  		return "team";
  	}
	
	/**
	 * Project
	 * @param model, HttpServletRequest
	 * @return
	 */
	@GetMapping("project")
  	public String project(Model model, HttpServletRequest request) {
		// 현재 서비스 위치 Set
		model.addAttribute("locationUrl", request.getAttribute("locationUrl"));
		model.addAttribute("locationName", request.getAttribute("locationName"));
		model.addAttribute("parentLocationName", request.getAttribute("parentLocationName"));
		
  		return "project";
  	}
	
	/**
	 * DashBoard
	 * @param model, HttpServletRequest
	 * @return
	 */
	@GetMapping("dashboard")
  	public String dashboard(Model model, HttpServletRequest request) {
		// 현재 서비스 위치 Set
		model.addAttribute("locationUrl", request.getAttribute("locationUrl"));
		model.addAttribute("locationName", request.getAttribute("locationName"));
		model.addAttribute("parentLocationName", request.getAttribute("parentLocationName"));
		
  		return "dashboard";
  	}
	
	/**
	 * Task(KanBan)
	 * @param model, HttpServletRequest
	 * @return
	 */
	@GetMapping("task")
  	public String task(Model model, HttpServletRequest request) {
		// 현재 서비스 위치 Set
		model.addAttribute("locationUrl", request.getAttribute("locationUrl"));
		model.addAttribute("locationName", request.getAttribute("locationName"));
		model.addAttribute("parentLocationName", request.getAttribute("parentLocationName"));
		
  		return "task";
  	}
	
}
