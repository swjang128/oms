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
import com.oms.dto.DepartmentDTO;
import com.oms.entity.Department;
import com.oms.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

/**
 * Department 서비스에 대한 Rest API
 * @author capias J
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class DepartmentAPI {
	@Autowired
	DepartmentService departmentService;
	
	/**
	 * 부서 목록 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("department")
	public Map<String, Object> read() {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 부서 목록 조회
		return departmentService.read(2, resultMap);
	}
	
	/**
	 * 부서 등록 (CREATE)
	 * @param RequestBody
	 * @throws IOException 
	 */
	@PostMapping("department")
	public Map<String, Object> create(@Valid @RequestBody DepartmentDTO departmentDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 부서 등록
		return departmentService.create(departmentDTO, resultMap);
	}
	
	/**
	 * 부서 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("department/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 부서 삭제
		return departmentService.delete(param, resultMap);
	}
	
	/**
	 * 부서 수정 (UPDATE)
	 * @param RequestBody
	 * @return 
	 */
	@PutMapping("department")
	public Map<String, Object> update(@Valid @RequestBody DepartmentDTO departmentDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 부서 수정
		return departmentService.update(departmentDTO, resultMap);
	}

	/**
	 * 사용/미사용 부서 목록 조회
	 * @param model
	 * @param request
	 * @return 
	 */
	@GetMapping("department/{useYn}")
	public Map<String, Object> readByUseYn(@PathVariable("useYn") Integer useYn) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 부서 목록 조회
		return departmentService.read(useYn, resultMap);
	}
}
