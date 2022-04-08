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
	public Map<String, Object> read() {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<HistoryDTO> historyList = new ArrayList<HistoryDTO>();
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		
		// 접속로그 목록 조회
		try {
			historyList = historyService.read();
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
			message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		}
				
		// RESTful API 결과를 리턴
		resultMap.put("historyList", historyList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		
		return resultMap;
	}
	
	/**
	 * 접속로그 정보 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("history/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		log.info("**** delete called");
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		int status = ResponseCode.Status.NOT_FOUND;		
		String message = ResponseCode.Message.NOT_FOUND;
		
		// 접속로그 정보 삭제
		try {
			result = historyService.delete(param);
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
	
}
