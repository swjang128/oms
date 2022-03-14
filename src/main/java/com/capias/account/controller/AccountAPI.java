package com.capias.account.controller;

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

import com.capias.admin.dto.MemberDTO;
import com.capias.admin.service.AccountService;
import com.capias.config.ResponseCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 계정 기능 관련 API
 * @author capias J
 */
@Controller
@Slf4j
@RequestMapping("api")
public class AccountAPI {
	@Autowired
	AccountService accountService;
	
	/*******************
	 * 이메일 중복 체크 *
	 *******************/
	@ResponseBody
	@GetMapping("account/{email}")
	public Map<String, Object> checkEmail(@PathVariable("email") String email) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;		
		log.info("****** 전달받은 email: {}", email);
		
		// email이 존재하는지 확인
		try {
			status = accountService.checkEmail(email);
			switch(status) {
				case ResponseCode.Status.OK -> message = ResponseCode.Message.OK;
				case ResponseCode.Status.ACCOUNT_DUPLICATE  -> message = ResponseCode.Message.ACCOUNT_DUPLICATE;
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		log.info("****** status: {}", status);
		log.info("****** message: {}", message);
		
		resultMap.put("status",  status);
		resultMap.put("message",  message);
		
		return resultMap;
	}
	
	/**
	 * 회원 가입
	 */
	@ResponseBody
	@PostMapping("account")
	public Map<String, Object> signUp(/* @Valid */ @RequestBody MemberDTO memberDTO, Model model) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		
		// payload로 받아온 파라미터를 서비스로 전송하여 회원 가입 처리
		log.info("****** 회원 가입 요청: {}", memberDTO);
		status = accountService.create(memberDTO);
		
		resultMap.put("status", status);
		resultMap.put("message", message);
		
		return resultMap;
	}
}
