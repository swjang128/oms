package com.oms.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oms.admin.dto.MemberDTO;
import com.oms.admin.service.AccountService;
import com.oms.config.ResponseCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 계정 기능 관련 컨트롤러
 * @author user
 *
 */
@Controller
@Slf4j
@RequestMapping("/account")
public class AccountController {
	@Autowired
	AccountService accountService;
	
	/**
	 * 로그인 페이지로 이동 (세션이 없을 때 Spring Security가 이 페이지를 매핑)
	 */
	@GetMapping("")
	public String main(Model model, @RequestParam(value="result", required=false) String result) {
		String message = "";
		log.info("**** result: {}", result);
		if (result != null) {
			switch(result) {
				case "notFoundOrBadCredentials" -> message = "이메일 또는 비밀번호를 확인해주세요.";
				case "accountExpired" -> message = "만료된 계정입니다.";
				case "credentialsExpired" -> message = "비밀번호가 만료되었습니다. 재설정 해주세요.";
				case "disabled" -> message = "휴면 계정입니다.";
				case "locked" -> message = "계정이 잠겨있습니다.";
				default -> message = "로그인 중 에러가 발생하였습니다.";
			}
		}
		
		log.info("**** message: {}", message);
		model.addAttribute("message", message);
		
		return "account/main";
	}
	
	/**
	 * 비밀번호 초기화 페이지로 이동
	 */
	@GetMapping("/resetPassword")
	public String resetPasswordWeb(Model model) {
		String message = "";
		
		log.info("message: {}", message);
		model.addAttribute("message", message);
		
		return "account/reset-password";
	}
	
	/**
	 * 비밀번호 초기화
	 */
	@ResponseBody
	@PostMapping("/resetPassword")
	public Map<String, Object> resetPassword(@RequestBody MemberDTO memberDTO) {
		// 기본 변수 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		String message = "존재하지 않거나 잘못된 메일 형식입니다.";
		
		status = accountService.resetPassword(memberDTO);
		
		log.info("status: {}", status);
		
		if (status == ResponseCode.Status.OK) {
			message = ResponseCode.Message.OK;
		}

		resultMap.put("status", status);
		resultMap.put("message", message);

		return resultMap;
	}
}
