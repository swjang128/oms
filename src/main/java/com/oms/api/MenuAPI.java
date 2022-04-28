package com.oms.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RestController;

import com.oms.config.ResponseCode;
import com.oms.dto.MenuDTO;
import com.oms.entity.Menu.UseYn;
import com.oms.service.MenuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 메뉴 목록 관리 Rest API
 * @author JSW
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class MenuAPI {
	@Autowired
	MenuService menuService;
	
	/**
	 * 메뉴 조회
	 * @param RequestParam
	 * @param request
	 * @return 
	 */
	@GetMapping("menu")
	public Map<String, Object> read(@RequestParam(name="use", required=false) List<UseYn> useYn,
																@RequestParam(name="parent", required=false) List<Long> parentId,
																@RequestParam(name="url", required=false) List<String> url) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 필요한 파라미터 Set
		paramMap.put("useYn", useYn);
		paramMap.put("parentId", parentId);
		paramMap.put("url", url);
		// 메뉴 목록 조회
		return menuService.read(paramMap, resultMap);
	}
	
	/**
	 * 메뉴 등록 (CREATE)
	 * @param RequestBody
	 * @throws IOException 
	 */
	@PostMapping("menu")
	public Map<String, Object> create(@Valid @RequestBody MenuDTO menuDTO, HttpServletRequest request) {
		// 기본 변수 및 생성자 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 필요한 파라미터 Set
		if (request.getRemoteHost() == null) {
			menuDTO.setRegistUser("");
		} else {
			menuDTO.setRegistUser(request.getRemoteUser());
		}		
		if (menuDTO.getUseYn() != UseYn.Y && menuDTO.getUseYn() != UseYn.N) {
			menuDTO.setUseYn(UseYn.N);
		}
		menuDTO.setRegistTime(LocalDateTime.now());
		// 메뉴 등록
		return menuService.create(menuDTO, resultMap);
	}
	
	/**
	 * 메뉴 수정 (UPDATE)
	 * @param RequestBody, HttpServletRequest
	 * @return Map<String, Object>
	 */
	@PutMapping("menu")
	public Map<String, Object> update(@RequestBody MenuDTO menuDTO, HttpServletRequest request) {
		// 기본 변수 및 생성자 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 필요한 파라미터 Set
		if (request.getRemoteHost() == null) {
			menuDTO.setUpdateUser("");
		} else {
			menuDTO.setUpdateUser(request.getRemoteUser());
		}		
		if (menuDTO.getUseYn() != UseYn.Y && menuDTO.getUseYn() != UseYn.N) {
			menuDTO.setUseYn(UseYn.N);
		}
		menuDTO.setUpdateTime(LocalDateTime.now());
		// 메뉴 수정
		return menuService.update(menuDTO, resultMap);
	}
	
	/**
	 * 메뉴 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("menu/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 메뉴 정보 삭제
		return menuService.delete(param, resultMap);
	}
}
