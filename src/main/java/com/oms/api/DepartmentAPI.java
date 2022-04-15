package com.oms.api;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oms.dto.DepartmentDTO;
import com.oms.entity.Department.UseYn;
import com.oms.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

/**
 * Department 서비스에 대한 Rest API
 * @author jsw
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
	public Map<String, Object> read(@RequestParam(name="use", required=false) List<UseYn> useYn,
																  @RequestParam(name="name", required=false) List<String> name) {
		// 기본 변수 설정		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("useYn", useYn);
		paramMap.put("name", name);
		// 부서 목록 조회
		return departmentService.read(paramMap, resultMap);
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

}
