package com.oms.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.admin.dto.LawDTO;
import com.oms.admin.entity.Law;
import com.oms.admin.repository.LawRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LawService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	LawRepository lawRepository;
		
	/** 
	 * 법안 목록 조회 (READ)
	 * @return List<Law>
	 */
	public List<LawDTO> read() {
		// 법안 목록 조회
		List<Law> LawList = lawRepository.findAll();
		List<LawDTO> result = LawList.stream()											 
									 .map(LawDTO::new)
									 .collect(Collectors.toList());
		
		log.info("###### LawList Result: {}", result);
		
		return result;
	}
	
	/** 
	 * 법안 등록 (CREATE)
	 * @return 등록한 법안 정보
	 */
	@Transactional
	public Law create(@RequestBody LawDTO lawDTO) {
		// 법안 등록 (CREATE)
		Law result = lawRepository.save(lawDTO.toEntity());

		return result;
	}
	
	/** 
	 * 법안 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Integer update(@RequestBody LawDTO lawDTO) {
		Long id = lawDTO.getId();		
		int result = 0;
		// 해당 법안이 있는지 확인
		lawRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 법안이 없습니다. id: "+id));
		
		try {
			// 법안 수정 (UPDATE)
			lawRepository.save(lawDTO.toEntity());
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * 법안 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> param) {
		int result = 0;
		
		// 법안 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				lawRepository.deleteById(param.get(p));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
