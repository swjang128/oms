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
import org.springframework.web.bind.annotation.RestController;

import com.oms.config.ResponseCode;
import com.oms.dto.PositionDTO;
import com.oms.entity.Position;
import com.oms.service.PositionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Position 서비스에 대한 Rest API
 * @author capias J
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class PositionAPI {
	@Autowired
	PositionService positionService;
	
	/**
	 * 직급 목록 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("position")
	public Map<String, Object> read() {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<PositionDTO> positionList = new ArrayList<PositionDTO>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		int useYn = 1;
		
		// 직급 목록 조회
		try {
			positionList = positionService.read(useYn);
			status = ResponseCode.Status.OK;
			message = ResponseCode.Message.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RESTful API 결과를 리턴
		resultMap.put("positionList", positionList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		
		return resultMap;
	}
	
	/**
	 * 직급 등록 (CREATE)
	 * @param RequestBody
	 * @throws IOException 
	 */
	@PostMapping("position")
	public Map<String, Object> create(@Valid @RequestBody PositionDTO positionDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;		
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		Position result = null;
		
		// 직급 등록
		result = positionService.create(positionDTO);
		if (result != null) {
			status = ResponseCode.Status.CREATED;
			message = ResponseCode.Message.CREATED;	
		}
		
		// RESTful API 결과를 리턴
		resultMap.put("result",  result);
		resultMap.put("status",  status);
		resultMap.put("message",  message);
		
		return resultMap;
	}
	
	/**
	 * 직급 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("position/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		log.info("**** delete called");
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		int status = ResponseCode.Status.NOT_FOUND;		
		String message = ResponseCode.Message.NOT_FOUND;
		
		// 직급 삭제
		try {
			result = positionService.delete(param);
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
	
	/**
	 * 직급 수정 (UPDATE)
	 * @param RequestBody
	 * @return 
	 */
	@PutMapping("position")
	public Map<String, Object> update(@Valid @RequestBody PositionDTO positionDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;		
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
//		Long id = payload.getId();
		
		// 직급 수정
		try {
			result = positionService.update(positionDTO);
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
//		resultMap.put("id", id);
		
		return resultMap;
	}

}