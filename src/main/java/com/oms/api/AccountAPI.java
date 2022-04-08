package com.oms.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oms.config.ResponseCode;
import com.oms.dto.AccountDTO;
import com.oms.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * Account 서비스에 대한 Rest API
 * @author capias J
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class AccountAPI {
	@Autowired
	AccountService accountService;
	
	/**
	 * 이메일 중복 체크
	 * @param email
	 * @return
	 */
	@ResponseBody
	@GetMapping("account/{email}")
	public Map<String, Object> checkEmail(@PathVariable("email") String email) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// email이 존재하는지 확인
		resultMap = accountService.checkEmail(email, resultMap);
		return resultMap;
	}
	
	/**
	 * 비밀번호 초기화
	 */
	@ResponseBody
	@PostMapping("account/resetPassword")
	public Map<String, Object> resetPassword(@RequestBody AccountDTO accountDTO) {
		// 기본 변수 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 비밀번호 초기화
		resultMap = accountService.resetPassword(accountDTO, resultMap);
		
		return resultMap;
	}
	
	/**
	 * 비밀번호 변경
	 */
	@ResponseBody
	@PostMapping("account/changePassword")
	public Map<String, Object> changePassword(@RequestBody AccountDTO accountDTO) {
		// 기본 변수 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 비밀번호 변경
		resultMap = accountService.changePassword(accountDTO, resultMap);
		log.info("****** resultMap: {}", resultMap);
		return resultMap;
	}
	
	/**
	 * 관리자 목록 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("account")
	public Map<String, Object> read() {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<AccountDTO> accountList = new ArrayList<AccountDTO>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		
		// 상품 목록 조회
		try {
			accountList = accountService.read();
			status = ResponseCode.Status.OK;
			message = ResponseCode.Message.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RESTful API 결과를 리턴
		resultMap.put("accountList", accountList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		
		return resultMap;
	}
	
	/**
	 * 관리자 등록 (CREATE)
	 * @param RequestBody
	 * @throws IOException 
	 */
	@PostMapping("account")
	public Map<String, Object> create(@Valid @RequestBody AccountDTO accountDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		// 직원 등록
		resultMap = accountService.create(accountDTO, resultMap);
		// RESTful API 결과를 리턴
		return resultMap;
	}
	
	/**
	 * 관리자 정보 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("account/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 관리자 정보 삭제
		resultMap = accountService.delete(param, resultMap);
		// 결과 리턴
		return resultMap;
	}
	
	/**
	 * 관리자 정보 수정 (UPDATE)
	 * @param RequestBody
	 * @return 
	 */
	@PutMapping("account")
	public Map<String, Object> update(@RequestBody AccountDTO accountDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 관리자 정보 수정
		resultMap = accountService.update(accountDTO, resultMap);
		// RESTful API 결과를 리턴
		return resultMap;
	}

}
