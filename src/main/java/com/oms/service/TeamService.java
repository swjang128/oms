package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.config.ResponseCode;
import com.oms.dto.TeamDTO;
import com.oms.entity.Team;
import com.oms.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 팀에 대한 서비스
 * @author JSW
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TeamService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	TeamRepository teamRepository;

	/**
	 * 팀 등록
	 * @param TeamDTO
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> create(TeamDTO teamDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// teamDTO를 Entity로 변환
		Team team = teamDTO.toEntity();
		// 팀 등록
		try {
			teamRepository.save(team);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}

	/**
	 * 팀 목록 조회 (READ)
	 * 
	 * @return List<Team>
	 */
	public Map<String, Object> read(Map<String, Object> resultMap) {
		// 기본 변수 설정
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Team> team = new ArrayList<Team>();
		List<TeamDTO> teamDTO = new ArrayList<TeamDTO>();
		// 팀 목록 조회
		try {
			team = teamRepository.findAll();
			teamDTO = team.stream().map(a -> modelMapper.map(a, TeamDTO.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// resultMap에 담기
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("teamList", teamDTO);		
		return resultMap;
	}
	
	
	/**
	 * 특정 팀 조회 (READ)
	 * @param Long
	 * @return Map<String, Object>
	 */
	public Map<String, Object> readOne(Long id, Map<String, Object> resultMap) {
		// 기본 변수 설정
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		Team team = null;
		TeamDTO teamDTO = null;
		// 특정 팀 조회
		try {			
			team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("팀이 존재하지 않습니다. id: " + id));
			teamDTO = new TeamDTO(team);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.TEAM_NOT_FOUND;
			message = ResponseCode.Message.TEAM_NOT_FOUND;
		}
		// resultMap에 담기
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("team", teamDTO);		
		return resultMap;
	}

	/**
	 * 팀 정보 수정 (UPDATE)
	 * 
	 * @param @RequestBody
	 * @return
	 * @return
	 */
	@Transactional
	public Map<String, Object> update(TeamDTO teamDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		long id = teamDTO.getId();
		Optional<Team> team = Optional.empty();
		// 해당 팀이 있는지 확인
		team = teamRepository.findById(id);
		if (!team.isPresent()) {
			status = ResponseCode.Status.TEAM_NOT_FOUND;
			message = ResponseCode.Message.TEAM_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}
		// 팀 정보 수정 (UPDATE)
		try {
			teamRepository.save(teamDTO.toEntity());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		resultMap.put("status", status);
		resultMap.put("message", message);
		
		return resultMap;
	}

	/**
	 * 팀 정보 삭제 (DELETE)
	 * 
	 * @return http 응답코드
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> payload, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// 대상 팀이 존재하는지 확인
		try {
			for (int p=0; p<payload.size(); p++) {
				teamRepository.findById(payload.get(p)).orElseThrow(() -> new IllegalArgumentException("payload 중 존재하지 않는 팀이 있습니다: " + payload));
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.TEAM_NOT_FOUND;
			message = ResponseCode.Message.TEAM_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);				
			return resultMap;
		}
		// 팀이 존재하면 삭제
		for (int d=0; d<payload.size(); d++) {
			try {
				teamRepository.deleteById(payload.get(d));
			} catch (Exception e) {
				e.printStackTrace();
				status = ResponseCode.Status.ERROR_ABORT;
				message = ResponseCode.Message.ERROR_ABORT;
				resultMap.put("status", status);
				resultMap.put("message", message);
			}
		}
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("deletedId", payload);
		return resultMap;
	}

	/**
	 * 팀명 중복 체크
	 * @param String
	 * @return Map<String, Object>
	 */
	public Map<String, Object> duplicateCheck(String name, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.TEAM_EXIST;
		String message = ResponseCode.Message.TEAM_EXIST;
		try {
			Optional<Team> team = teamRepository.findByName(name);
			if (!team.isPresent()) {
				status = ResponseCode.Status.TEAM_NOT_FOUND;
				message = ResponseCode.Message.TEAM_NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
}

