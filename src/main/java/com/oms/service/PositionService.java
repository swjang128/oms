package com.oms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.config.ResponseCode;
import com.oms.dto.PositionDTO;
import com.oms.entity.Position;
import com.oms.repository.PositionRepository;

@Service
public class PositionService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	PositionRepository positionRepository;
		
	/** 
	 * 직급 목록 조회 (READ)
	 * @return List<Position>
	 */
	public Map<String, Object> read(int useYn, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Position> positions =null;
		List<PositionDTO> positionList = null;
		// 직급 목록 조회 (사용하는 직급만)
		try {
			if (useYn!=0 && useYn!=1) {
				positions = positionRepository.findAll();				
			} else {
				positions = positionRepository.findByUseYn(useYn);
			}
			positionList = positions.stream().map(PositionDTO::new).collect(Collectors.toList());
			resultMap.put("positionList", positionList);
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
		// 직급 등록 (CREATE)
		try {
			LocalDateTime now = LocalDateTime.now();
			positionDTO.setRegistDate(now);
			if (positionDTO.getUseYn() == null) {
				positionDTO.setUseYn(1);
			}
			positionRepository.save(positionDTO.toEntity());
			resultMap.put("position", positionDTO);
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
			if (positionDTO.getUseYn()!=0 && positionDTO.getUseYn()!=1) {
				positionDTO.setUseYn(1);
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
