package com.oms.api;

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

import com.oms.service.HistoryService;

import lombok.extern.slf4j.Slf4j;

/**
 * 접속로그 Rest API
 * @author jsw
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class HistoryAPI {
	@Autowired
	HistoryService historyService;
	
	/**
	 * 접속로그 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("history")
	public Map<String, Object> read(@RequestParam(name="status", required=false) List<Integer> status,
																 @RequestParam(name="host", required=false) List<String> host,
																 @RequestParam(name="clientIp", required=false) List<String> clientIp,
																 @RequestParam(name="method", required=false) List<String> method,
																 @RequestParam(name="requestUri", required=false) List<String> requestUri,
																 @RequestParam(name="startTime", required=false) String startTime,
																 @RequestParam(name="endTime", required=false) String endTime) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		paramMap.put("host", host);
		paramMap.put("clientIp", clientIp);
		paramMap.put("method", method);
		paramMap.put("requestUri", requestUri);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		return historyService.read(paramMap, resultMap);
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
