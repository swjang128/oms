package com.oms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.dto.PositionDTO;
import com.oms.entity.Position;
import com.oms.repository.PositionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PositionService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	PositionRepository positionRepository;
		
	/** 
	 * 직급 목록 조회 (READ)
	 * @return List<Position>
	 */
	public List<PositionDTO> read(int useYn) {
		// 직급 목록 조회 (사용하는 직급만)
		//useYn = 1;
		List<Position> PositionList = positionRepository.findByUseYn(useYn);
		List<PositionDTO> result = PositionList.stream()											 
									 .map(PositionDTO::new)
									 .collect(Collectors.toList());
		
		log.info("###### PositionList Result: {}", result);
		
		return result;
	}
	
	/** 
	 * 직급 등록 (CREATE)
	 * @return 등록한 직급 정보
	 */
	@Transactional
	public Position create(@RequestBody PositionDTO positionDTO) {
		// 직급 등록 (CREATE)
		Position result = positionRepository.save(positionDTO.toEntity());

		return result;
	}
	
	/** 
	 * 직급 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Integer update(@RequestBody PositionDTO positionDTO) {
		Long id = positionDTO.getId();		
		int result = 0;
		// 해당 직급이 있는지 확인
		positionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 직급이 없습니다. id: "+id));
		
		try {
			// 직급 수정 (UPDATE)
			positionRepository.save(positionDTO.toEntity());
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * 직급 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> param) {
		int result = 0;
		
		// 직급 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				positionRepository.deleteById(param.get(p));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
