package com.oms.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oms.config.ResponseCode;
import com.oms.dto.MenuDTO;
import com.oms.service.MenuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 메뉴 목록 관리 Rest API
 * @author capias J
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class MenuAPI {
	@Autowired
	MenuService menuService;
	
	/**
	 * 메뉴 전체 목록 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("menu")
	public Map<String, Object> read() {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MenuDTO> menuList = new ArrayList<MenuDTO>();
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		
		// 메뉴 목록 조회
		try {
			menuList = menuService.read();
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
			message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		}
				
		// RESTful API 결과를 리턴
		resultMap.put("menuList", menuList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		
		return resultMap;
	}
	
	/**
	 * 하위 메뉴 조회 (메인 메뉴는 파라미터를 0)
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("menu/{id}")
	public Map<String, Object> read(@PathVariable("id") Long parentId) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MenuDTO> menuList = new ArrayList<MenuDTO>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		
		// 하위 메뉴 조회
		try {
			menuList = menuService.read(parentId);			
			status = ResponseCode.Status.OK;
			message = ResponseCode.Message.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RESTful API 결과를 리턴
		resultMap.put("menuList", menuList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		
		return resultMap;
	}
	
	/**
	 * 메뉴 등록 (CREATE)
	 * @param RequestBody
	 * @throws IOException 
	 */
	@PostMapping("menu")
	public Map<String, Object> create(@Valid @RequestBody MenuDTO menuDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;		
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		String result = "";
		
		try {
			menuService.create(menuDTO);
			
			status = ResponseCode.Status.CREATED;
			message = ResponseCode.Message.CREATED;			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// RESTful API 결과를 리턴
		resultMap.put("result",  result);
		resultMap.put("status",  status);
		resultMap.put("message",  message);
		
		return resultMap;
	}
	
	/**
	 * 메뉴 정보 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("menu/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		log.info("**** delete called");
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		int status = ResponseCode.Status.NOT_FOUND;		
		String message = ResponseCode.Message.NOT_FOUND;
		
		// 메뉴 정보 삭제
		try {
			result = menuService.delete(param);
			if (result > 0) {
				status = ResponseCode.Status.OK;
				message = ResponseCode.Message.OK;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RESTful API 결과를 리턴
		resultMap.put("status",  status);
		resultMap.put("message",  message);
		
		return resultMap;
	}
	
	/**
	 * 메뉴 정보 수정 (UPDATE)
	 * @param RequestBody
	 * @return 
	 */
	@PutMapping("menu")
	public Map<String, Object> update(@RequestBody MenuDTO menuDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;		
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		
		// 메뉴 정보 수정
		try {
			result = menuService.update(menuDTO);
			if (result == 1) {
				status = ResponseCode.Status.OK;
				message = ResponseCode.Message.OK;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RESTful API 결과를 리턴
		resultMap.put("status",  status);
		resultMap.put("message",  message);
		
		return resultMap;
	}

}
