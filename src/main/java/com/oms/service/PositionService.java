package com.oms.service;

import java.time.LocalDateTime;
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
import com.oms.entity.Position;
import com.oms.entity.Position.UseYn;
import com.oms.repository.PositionRepository;
import com.oms.specification.PositionSpecification;

@Service
public class PositionService{
	@Autowired
	PositionRepository positionRepository;
		
	/** 
	 * 직급 목록 조회 (READ)
	 * @return List<Position>
	 */
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Position> position =null;
		List<PositionDTO> positionDTO = null;
		Object useYn = paramMap.get("useYn");
		Object name = paramMap.get("name");
		Specification<Position> specification = (root, query, criteriaBuilder) -> null;
		// 직급 목록 조회
		try {
			if (useYn != null)
				specification = specification.and(PositionSpecification.findByUseYn(useYn));
			if (name != null)
				specification = specification.and(PositionSpecification.findByName(name));
			position = positionRepository.findAll(specification);
			positionDTO = position.stream().map(PositionDTO::new).collect(Collectors.toList());
			resultMap.put("positionList", positionDTO);
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
	 * 직급 등록 (CREATE)
	 * @return 등록한 직급 정보
	 */
	@Transactional
	public Map<String, Object> create(@RequestBody PositionDTO positionDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.CREATED;
		String message = ResponseCode.Message.CREATED;
		Position position = null;
		// 직급 등록 (CREATE)
		try {
			LocalDateTime now = LocalDateTime.now();
			positionDTO.setRegistDate(now);
			if (positionDTO.getUseYn() == null) {
				positionDTO.setUseYn(UseYn.Y);
			}
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
	 * 직급 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Map<String, Object> update(@RequestBody PositionDTO positionDTO, Map<String, Object> resultMap) {
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
			LocalDateTime now = LocalDateTime.now();
			positionDTO.setUpdateDate(now);
			if (positionDTO.getUseYn()!=UseYn.Y && positionDTO.getUseYn()!=UseYn.N) {
				positionDTO.setUseYn(UseYn.Y);
			}
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
