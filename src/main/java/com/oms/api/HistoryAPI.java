package com.oms.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oms.config.ResponseCode;
import com.oms.dto.HistoryDTO;
import com.oms.service.HistoryService;

import lombok.extern.slf4j.Slf4j;

/**
 * 접속로그 목록 관리 Rest API
 * @author capias J
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class HistoryAPI {
	@Autowired
	HistoryService historyService;
	
	/**
	 * 접속로그 전체 목록 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("history")
	public Map<String, Object> read(@RequestParam(required=false) List<Integer> status) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (status!=null) {
			return historyService.findByStatus(status, resultMap);	
		}
		return historyService.read(resultMap);
	}
	
	/**
	 * 접속로그 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("history/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 접속로그 삭제
		return historyService.delete(param, resultMap);
	}
	
}
