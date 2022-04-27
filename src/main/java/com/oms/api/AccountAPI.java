package com.oms.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oms.dto.AccountDTO;
import com.oms.entity.Account.Role;
import com.oms.entity.Account.Status;
import com.oms.entity.Account.UserStatus;
import com.oms.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * Account 서비스에 대한 Rest API
 * 
 * @author jsw
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class AccountAPI {
	@Autowired
	AccountService accountService;

	/**
	 * 계정 목록 조회
	 * 
	 * @param @RequestParam
	 * @return Map<String, Object>
	 */
	@GetMapping("account")
	public Map<String, Object> read(@RequestParam(required=false) List<Status> status,
																 @RequestParam(required=false) List<UserStatus> userStatus,
																 @RequestParam(required=false) List<Role> role,
																 @RequestParam(required=false) List<String> department,
																 @RequestParam(required=false) List<String> position) {
		// 기본 변수 설정
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		paramMap.put("userStatus", userStatus);
		paramMap.put("role", role);
		paramMap.put("department", department);
		paramMap.put("position", position);
		// 계정 목록 조회
		return accountService.read(paramMap, resultMap);
	}

	/**
	 * 계정 등록 (CREATE)
	 * 
	 * @param RequestBody
	 * @throws IOException
	 */
	@PostMapping("account")
	public Map<String, Object> create(@Valid @RequestBody AccountDTO accountDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 직원 등록
		return accountService.create(accountDTO, resultMap);
	}

	/**
	 * 특정 계정 조회 (READ)
	 * 
	 * @param PathVariable
	 * @return Map<String, Object>
	 */
	@GetMapping("account/{id}")	
	public Map<String, Object> readOne(@PathVariable("id") Long param) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 계정 정보 삭제
		return accountService.readOne(param, resultMap);
	}
	
	/**
	 * 계정 정보 삭제 (DELETE)
	 * 
	 * @param PathVariable
	 * @return
	 */
	@DeleteMapping("account/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> payload) {
		log.info("****** 삭제할 아이디: {}", payload);
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 계정 정보 삭제
		return accountService.delete(payload, resultMap);
	}

	/**
	 * 계정 정보 수정 (UPDATE)
	 * 
	 * @param RequestBody
	 * @return
	 */
	@PutMapping("account")
	public Map<String, Object> update(@RequestBody AccountDTO accountDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 계정 정보 수정
		return accountService.update(accountDTO, resultMap);
	}

	/**
	 * 이메일 중복 체크
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("checkEmail/{email}")
	public Map<String, Object> checkEmail(@PathVariable("email") String email) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// email이 존재하는지 확인
		return accountService.checkEmail(email, resultMap);
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
		return accountService.resetPassword(accountDTO, resultMap);
	}

	/**
	 * 비밀번호 변경
	 */
	@ResponseBody
	@PostMapping("account/updatePassword")
	public Map<String, Object> updatePassword(@RequestBody AccountDTO accountDTO) {
		// 기본 변수 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 비밀번호 변경
		return accountService.updatePassword(accountDTO, resultMap);
	}
	
	/**
	 * 상태(userStatus) 변경
	 */
	@ResponseBody
	@PutMapping("account/updateUserStatus")
	public Map<String, Object> updateUserStatus(@RequestBody AccountDTO accountDTO, HttpSession session) {
		// 기본 변수 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 비밀번호 변경
		resultMap = accountService.updateUserStatus(accountDTO, resultMap);
		// 세션값 변경
		session.setAttribute("sessionUserStatus", accountDTO.getUserStatus().getValue());
		return resultMap;
	}

}
