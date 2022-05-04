package com.oms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.config.ResponseCode;
import com.oms.dto.PositionDTO;
import com.oms.entity.Department;
import com.oms.entity.Position;
import com.oms.entity.Position.UseYn;
import com.oms.repository.PositionRepository;
import com.oms.specification.DepartmentSpecification;
import com.oms.specification.PositionSpecification;

@Service
public class PositionService{
	@Autowired
	PositionRepository positionRepository;
	
	/** 
	 * 직급 등록 (CREATE)
	 * @param PositionDTO, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> create(PositionDTO positionDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.CREATED;
		String message = ResponseCode.Message.CREATED;
		Position position = null;
		// 직급 등록 (CREATE)
		try {
			position = positionRepository.save(positionDTO.toEntity());
			resultMap.put("position", position);
		} catch (Exception e ) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/** 
	 * 직급 목록 조회 (READ)
	 * @return Map<String, Object>
	 */
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Position> position =new ArrayList<Position>();
		List<PositionDTO> positionDTO = new ArrayList<PositionDTO>();
		Object useYn = paramMap.get("useYn");
		Object name = paramMap.get("name");
		Specification<Position> specification = (root, query, criteriaBuilder) -> null;
		// Specification 설정
		if (useYn != null)
			specification = specification.and(PositionSpecification.findByUseYn(useYn));
		if (name != null)
			specification = specification.and(PositionSpecification.findByName(name));
		// 직급 목록 조회
		try {
			position = positionRepository.findAll(specification);
			positionDTO = position.stream().map(PositionDTO::new).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴		
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("positionList", positionDTO);
		return resultMap;
	}
	
	/**
	 * 직급 개수 (READ)
	 * @param Map<String, Object>, Map<String, Object>
	 * @return Integer
	 */
	public Map<String, Object> count(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		// 기본 변수 설정
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		long count = 0;
		Object useYn = paramMap.get("useYn");
		Object name = paramMap.get("name");
		Specification<Position> specification = (root, query, criteriaBuilder) -> null;
		// Specification 조건 작성
		if (useYn != null)
			specification = specification.and(PositionSpecification.findByUseYn(useYn));
		if (name != null) {
			specification = specification.and(PositionSpecification.findByName(name));
		}
		// Specification 조건에 맞는 직급 개수 조회
		count = positionRepository.count(specification);
		// resultMap에 담아서 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("count", count);
		return resultMap;
	}
	
	/** 
	 * 직급 수정 (UPDATE)
	 * @param PositionDTO, Map<String, Object>
	 * @return Map<String, Object> 
	 */
	@Transactional
	public Map<String, Object> update(PositionDTO positionDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// 해당 직급이 있는지 확인
		try {
			positionRepository.findById(positionDTO.getId()).orElseThrow(() -> new IllegalArgumentException("해당 직급이 없습니다. id: "+positionDTO.getId()));	
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.POSITION_NOT_FOUND;
			message = ResponseCode.Message.POSITION_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}
		// 직급 수정 (UPDATE)
		try {
			positionRepository.save(positionDTO.toEntity());
			resultMap.put("position", positionDTO);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/** 
	 * 직급 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> param, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// 직급 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				positionRepository.deleteById(param.get(p));
			}
			resultMap.put("id", param);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}

}
