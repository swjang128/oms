package com.oms.api;

import java.io.IOException;
import java.time.LocalDateTime;
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

import com.oms.dto.PositionDTO;
import com.oms.entity.Position.UseYn;
import com.oms.service.PositionService;

/**
 * 직급에 대한 Rest API
 * @author jsw
 *
 */
@RestController
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
	public Map<String, Object> read(@RequestParam(name="use", required=false) List<UseYn> useYn,
																  @RequestParam(name="name", required=false) List<String> name) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("useYn", useYn);
		paramMap.put("name", name);
		// 직급 목록 조회
		return positionService.read(paramMap, resultMap);
	}
	
	/**
	 * 직급 등록 (CREATE)
	 * @param RequestBody, HttpServletRequest
	 * @throws IOException 
	 */
	@PostMapping("position")
	public Map<String, Object> create(@Valid @RequestBody PositionDTO positionDTO, HttpServletRequest request) {
		// 기본 변수 및 생성자 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 필요한 파라미터 Set
		if (request.getRemoteHost() == null) {
			positionDTO.setRegistUser("");
		} else {
			positionDTO.setRegistUser(request.getRemoteUser());
		}		
		if (positionDTO.getUseYn() != UseYn.Y && positionDTO.getUseYn() != UseYn.N) {
			positionDTO.setUseYn(UseYn.Y);
		}
		positionDTO.setRegistTime(LocalDateTime.now());
		// 직급 등록
		return positionService.create(positionDTO, resultMap);
	}
	
	/**
	 * 직급 수정 (UPDATE)
	 * @param RequestBody, HttpServletRequest
	 * @return Map<String, Object>
	 */
	@PutMapping("position")
	public Map<String, Object> update(@Valid @RequestBody PositionDTO positionDTO, HttpServletRequest request) {
		// 기본 변수 및 생성자 선언
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 필요한 파라미터 Set
		if (request.getRemoteHost() == null) {
			positionDTO.setUpdateUser("");
		} else {
			positionDTO.setUpdateUser(request.getRemoteUser());
		}		
		if (positionDTO.getUseYn() != UseYn.Y && positionDTO.getUseYn() != UseYn.N) {
			positionDTO.setUseYn(UseYn.Y);
		}
		positionDTO.setUpdateTime(LocalDateTime.now());
		// 직급 수정
		return positionService.update(positionDTO, resultMap);
	}
	
	/**
	 * 직급 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("position/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 직급 삭제
		return positionService.delete(param, resultMap);
	}
}
