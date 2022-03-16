package com.oms.admin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oms.admin.dto.HistoryDTO;
import com.oms.admin.dto.LawDTO;
import com.oms.admin.dto.MemberDTO;
import com.oms.admin.dto.MenuDTO;
import com.oms.admin.dto.ProductDTO;
import com.oms.admin.dto.TaskDTO;
import com.oms.admin.service.AccountService;
import com.oms.admin.service.HistoryService;
import com.oms.admin.service.LawService;
import com.oms.admin.service.MenuService;
import com.oms.admin.service.ProductService;
import com.oms.admin.service.TaskService;
import com.oms.config.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Admin 기능의 뷰 호출을 위한 컨트롤러
 * @author capias J
 *
 */
@Controller
@Slf4j
@RequestMapping("admin")
public class AdminController {
	@Autowired
	AccountService accountService;
	@Autowired
	ProductService productService;
	@Autowired
	LawService lawService;
	@Autowired
	MenuService menuService;
	@Autowired
	HistoryService historyService;
	@Autowired
	TaskService taskService;
	@Autowired
	CommonUtil commonUtil;
	
	
	/************************************* Admin 페이지 ****************************************/
	/**
	 * Admin Dashboard 페이지로 이동
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
		
		// 기본 변수 설정
		int monthly = 1782900;	// 당월 매출액
		int yearly = monthly;	// 당해 매출액 
		int achievementRate = 7;	// 목표 매출액
		int orders = 49;	// 당월 주문수량
		int ratioHW = 42;	// 매출액 대비 하드웨어 비율
		int ratioSW = 37;	// 매출액 대비 소프트웨어 비율
		int ratioETC = 100-(ratioHW+ratioSW);	// 매출액 대비 주변기기 비율
		
		// 매출 지표 조회

		// 당월 매출액
		model.addAttribute("monthly", monthly);
		// 당해 매출액
		model.addAttribute("yearly", yearly);
		// 목표 매출액 달성율
		model.addAttribute("achievementRate", achievementRate);
		// 당해 주문수량
		model.addAttribute("orders", orders);
		// 월별 매출액 차트		
		// 품목별 매출현황
		model.addAttribute("ratioHW", ratioHW);
		model.addAttribute("ratioSW", ratioSW);
		model.addAttribute("ratioETC", ratioETC);
		
		log.info("------------- model: {}", model);
		// 메인 페이지로 호출
		return "/admin/dashboard";
  	}
	
	/**
	 * 직원 페이지로 이동
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("member")
	public Model member(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
		// 직원 목록 조회
		List<MemberDTO> memberList = accountService.read();
		model.addAttribute("memberList", memberList);
		
		return model;
	}

	/**
	 * 상품 페이지로 이동
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("product")
	public Model product(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
		// 상품 목록 조회
		List<ProductDTO> productList = productService.read();
		model.addAttribute("productList", productList);
		
		return model;
	}
	
	/**
	 * 법안 페이지로 이동
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("law")
	public Model law(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
		// 법안 목록 조회
		List<LawDTO> lawList = lawService.read();
		model.addAttribute("lawList", lawList);
		
		return model;
	}
	
	/**
	 * 메뉴 페이지로 이동
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("menu")
	public Model menu(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
		// 법안 목록 조회
		List<MenuDTO> menuList = menuService.read();
		model.addAttribute("menuList", menuList);
		
		return model;
	}
	
	/**
	 * API 호출 로그 페이지로 이동
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("history")
	public Model history(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
		// API 호출 로그 목록 조회
		List<HistoryDTO> historyList = historyService.read();
		model.addAttribute("historyList", historyList);
		
		return model;
	}
	
	/**
	 * 메뉴 페이지로 이동
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("task")
	public Model task(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
		// 법안 목록 조회
		List<TaskDTO> taskList = taskService.read();
		model.addAttribute("taskList", taskList);
		
		return model;
	}
	
	
	
	/************************************* 분류없음 (임시로 사이드바 기능들 매핑) ****************************************/ 
	/**
	 * 고객
	 * @param model
	 * @return
	 */
	@GetMapping("customer")
  	public String customer(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "admin/buttons";
  	}
	
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
		
  		return "admin/tables";
  	}
	
	/**
	 * Table Sample 2
	 * @param model
	 * @return
	 */
	@GetMapping("tables2")
  	public String tables2(Model model) {
		// ServletPath, CurrentLocation 설정 
		String servletPath = commonUtil.getServletPath();
		String currentLocation = commonUtil.getCurrentLocation();
		model.addAttribute("servletPath", servletPath);
		model.addAttribute("currentLocation", currentLocation);
		
  		return "admin/tables2";
  	}
}
