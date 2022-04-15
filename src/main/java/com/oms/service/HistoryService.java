package com.oms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.config.ResponseCode;
import com.oms.dto.HistoryDTO;
import com.oms.entity.Account;
import com.oms.entity.History;
import com.oms.repository.HistoryRepository;
import com.oms.specification.AccountSpecification;
import com.oms.specification.HistorySpecification;

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
	@SuppressWarnings("unchecked")
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<History> history = new ArrayList<History>();
		List<HistoryDTO> historyDTO = new ArrayList<HistoryDTO>();
		List<Integer> httpStatus = (List<Integer>) paramMap.get("status");
		Object host = paramMap.get("host");
		Object clientIp = paramMap.get("clientIp");
		Object method = paramMap.get("method");
		Object requestUri = paramMap.get("requestUri");	
		LocalDateTime startDate = LocalDateTime.of(1970, 01, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.now();
		if (paramMap.get("startDate") != null) {
			startDate = LocalDateTime.parse((String) paramMap.get("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));	
		} 
		if (paramMap.get("endDate") != null) {
			endDate = LocalDateTime.parse((String) paramMap.get("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		Specification<History> specification = (root, query, criteriaBuilder) -> null;
		// 접속로그 전체 조회
		try {
			if (httpStatus != null)
				specification = specification.and(HistorySpecification.findByStatus(httpStatus));
			if (host != null)
				specification = specification.and(HistorySpecification.findByHost(host));
			if (clientIp != null)
				specification = specification.and(HistorySpecification.findByClientIp(clientIp));
			if (method != null)
				specification = specification.and(HistorySpecification.findByMethod(method));
			if (requestUri != null)
				specification = specification.and(HistorySpecification.findByRequestUri(requestUri));
			specification = specification.and(HistorySpecification.findByRequestDate(startDate, endDate));
				
			history = historyRepository.findAll(specification);
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
		try {
			historyRepository.save(historyDTO.toEntity());	
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
}
