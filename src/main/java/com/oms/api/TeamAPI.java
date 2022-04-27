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
import org.springframework.web.bind.annotation.RestController;

import com.oms.dto.TeamDTO;
import com.oms.service.TeamService;
import lombok.extern.slf4j.Slf4j;

/**
 * 팀에 대한 Rest API
 * @author JSW
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class TeamAPI {
	@Autowired
	TeamService teamService;
	
	/**
	 * 팀 등록 (CREATE)
	 * @param RequestBody
	 * @throws IOException 
	 */
	@PostMapping("team")
	public Map<String, Object> create(@Valid @RequestBody TeamDTO teamDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 팀 등록
		return teamService.create(teamDTO, resultMap);
	}
	
	/**
	 * 팀 목록 조회
	 * @return Map<String, Object> 
	 */
	@GetMapping("team")
	public Map<String, Object> read() {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return teamService.read(resultMap);
	}
	
	/**
	 * 팀 정보 수정 (UPDATE)
	 * @param RequestBody
	 * @return 
	 */
	@PutMapping("team")
	public Map<String, Object> update(@RequestBody TeamDTO teamDTO) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 팀 정보 수정
		return teamService.update(teamDTO, resultMap);
	}
	
	/**
	 * 팀 삭제 (DELETE)
	 * @param PathVariable
	 * @return 
	 */
	@DeleteMapping("team/{id}")
	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
		// 기본 변수 설정
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 팀 삭제
		return teamService.delete(param, resultMap);
	}
}
