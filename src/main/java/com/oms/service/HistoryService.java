package com.oms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.dto.HistoryDTO;
import com.oms.entity.History;
import com.oms.repository.HistoryRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 카테고리 목록을 관리하는 Service
 * @author Capias J
 *
 */
@Service
@Slf4j
public class HistoryService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	HistoryRepository historyRepository;

	/** 
	 * 접속로그 목록 조회 (READ)
	 * @return List<HistoryDTO>
	 */
	public List<HistoryDTO> read() {
		// 접속로그 목록 조회
		List<History> historyList = historyRepository.findAll();
		List<HistoryDTO> result = historyList.stream()											 
									 .map(HistoryDTO::new)
									 .collect(Collectors.toList());
		
		log.info("###### HistoryList Result: {}", result);
		
		return result;
	}
	
	/** 
	 * 접속로그 조회 - email 검색 (READ)
	 * 메인 카테고리는 historyId를 0으로 받아서 조회한다
	 * @return List<HistoryDTO>
	 */
	public List<HistoryDTO> findByEmail(Long historyId) {		
		return null;
	}
	
	/** 
	 * 접속로그 등록 (CREATE)
	 * @return 등록한 접속로그 정보
	 */
	@Transactional
	public History create(@RequestBody  HistoryDTO historyDTO) {
		// 카테고리 등록 (CREATE)
		History result = historyRepository.save(historyDTO.toEntity());

		return result;
	}
	
	/** 
	 * 카테고리 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> param) {
		int result = 0;
		
		// 카테고리 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				historyRepository.deleteById(param.get(p));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
