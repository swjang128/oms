package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oms.config.ResponseCode;
import com.oms.dto.PositionDTO;
import com.oms.entity.Position;
import com.oms.repository.PositionRepository;
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
		ResponseCode result = ResponseCode.CREATED;
		Position position = null;
		// 직급 등록 (CREATE)
		try {
			position = positionRepository.save(positionDTO.toEntity());
			resultMap.put("position", position);
		} catch (Exception e ) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}
	
	/** 
	 * 직급 목록 조회 (READ)
	 * @return Map<String, Object>
	 */
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
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
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴		
		resultMap.put("result", result);
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
		ResponseCode result = ResponseCode.SUCCESS;
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
		try {
			count = positionRepository.count(specification);	
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// resultMap에 담아서 리턴
		resultMap.put("result", result);
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
		ResponseCode result = ResponseCode.SUCCESS;
		// 해당 직급이 있는지 확인
		try {
			positionRepository.findById(positionDTO.getId()).orElseThrow(() -> new IllegalArgumentException("해당 직급이 없습니다. id: "+positionDTO.getId()));	
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.POSITION_DOES_NOT_EXISTS;
			resultMap.put("result", result);
			return resultMap;
		}
		// 직급 수정 (UPDATE)
		try {
			positionRepository.save(positionDTO.toEntity());
			resultMap.put("position", positionDTO);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}
	
	/** 
	 * 직급 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> param, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
		// 직급 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				positionRepository.deleteById(param.get(p));
			}
			resultMap.put("id", param);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}

}
