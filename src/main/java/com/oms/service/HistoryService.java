package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.config.ResponseCode;
import com.oms.dto.HistoryDTO;
import com.oms.entity.History;
import com.oms.repository.HistoryRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 접속로그 Service
 * 
 * @author jsw
 *
 */
@Service
@Slf4j
public class HistoryService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	HistoryRepository historyRepository;

	/**
	 * 접속로그 전체 조회 (READ)
	 * @param Map<String, Object> resultMap
	 * @return List<HistoryDTO>
	 */
	public Map<String, Object> read(Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<History> history = new ArrayList<History>();
		List<HistoryDTO> historyDTO = new ArrayList<HistoryDTO>();		
		// 접속로그 전체 조회
		try {
			history = historyRepository.findAll();
			historyDTO = history.stream().map(HistoryDTO::new).collect(Collectors.toList());
			resultMap.put("historyList", historyDTO);
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
	 * 접속로그 등록 (CREATE)
	 * 
	 * @return 등록한 접속로그 정보
	 */
	@Transactional
	public void create(@RequestBody HistoryDTO historyDTO) {
		historyRepository.save(historyDTO.toEntity());
	}

	/**
	 * 접속내역 삭제 (DELETE)
	 * 
	 * @return http 응답코드
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> param, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// 카테고리 삭제 (DELETE)
		try {
			for (int p = 0; p < param.size(); p++) {
				historyRepository.deleteById(param.get(p));
			}
			resultMap.put("id", param);
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
	 * 특정 Status의 접속로그 조회 (READ)
	 * @param Map<String, Object> resultMap
	 * @return List<HistoryDTO>
	 */
	public Map<String, Object> findByStatus(List<Integer> httpStatus, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<History> history = new ArrayList<History>();
		List<HistoryDTO> historyDTO = new ArrayList<HistoryDTO>();		
		// 접속로그 전체 조회
		try {
			for (int s=0; s<httpStatus.size(); s++) {
				history = historyRepository.findByStatus(httpStatus.get(s));
				historyDTO.addAll(history.stream().map(HistoryDTO::new).collect(Collectors.toList()));
			}
			resultMap.put("historyList", historyDTO);
			resultMap.put("httpStatus", httpStatus);
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
