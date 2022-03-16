package com.oms.admin.controller;

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

import com.oms.admin.dto.TaskDTO;
import com.oms.admin.service.TaskService;
import com.oms.config.ResponseCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 업무 서비스에 대한 Rest API
 * @author capias J
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class TaskAPI {
	@Autowired
	TaskService taskService;
	
	/**
	 * 업무 전체 목록 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("task")
	public Map<String, Object> read() {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<TaskDTO> taskList = new ArrayList<TaskDTO>();
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		
		// 업무 목록 조회
		try {
			taskList = taskService.read();
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
			message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		}
				
		// RESTful API 결과를 리턴
		resultMap.put("taskList", taskList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		
		return resultMap;
	}
	
	/**
	 * 상위 업무 조회 (상위 업무는 파라미터를 0)
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("task/{id}")
	public Map<String, Object> read(@PathVariable("id") Long parentId) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<TaskDTO> taskList = new ArrayList<TaskDTO>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		
		// 상위 업무 조회
		try {
			taskList = taskService.read(parentId);			
			status = ResponseCode.Status.OK;
			message = ResponseCode.Message.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RESTful API 결과를 리턴
		resultMap.put("taskList", taskList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		
		return resultMap;
	}
	
	/**
	 * 업무 등록 (CREATE)
	 * @param RequestBody
	 * @throws IOException 
	 */
	@PostMapping("task")
	public Map<String, Object> create(@Valid @RequestBody TaskDTO taskDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;		
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		String result = "";
		
		try {
			taskService.create(taskDTO);
			
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
	 * 업무 정보 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("task/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		log.info("**** delete called");
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		int status = ResponseCode.Status.NOT_FOUND;		
		String message = ResponseCode.Message.NOT_FOUND;
		
		// 업무 정보 삭제
		try {
			result = taskService.delete(param);
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
	 * 업무 정보 수정 (UPDATE)
	 * @param RequestBody
	 * @return 
	 */
	@PutMapping("task")
	public Map<String, Object> update(@RequestBody TaskDTO taskDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		int status = ResponseCode.Status.INTERNAL_SERVER_ERROR;		
		String message = ResponseCode.Message.INTERNAL_SERVER_ERROR;
		
		// 업무 정보 수정
		try {
			result = taskService.update(taskDTO);
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
