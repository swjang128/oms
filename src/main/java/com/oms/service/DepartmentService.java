package com.oms.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.config.ResponseCode;
import com.oms.dto.DepartmentDTO;
import com.oms.entity.Department;
import com.oms.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	DepartmentRepository departmentRepository;

	/**
	 * 부서 목록 조회 (READ)
	 * 
	 * @return List<Department>
	 */
	public Map<String, Object> read(Integer useYn, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Department> departments = null;
		List<DepartmentDTO> departmentList = null;
		
		// 부서 목록 조회 (0: 사용하지 않는 부서, 1: 사용하는 부서, 그외: 전체 부서)
		try {
			if (useYn!=0 && useYn!=1) {
				departments = departmentRepository.findAll();	
			} else {
				departments = departmentRepository.findByUseYn(useYn);
			}
			departmentList = departments.stream().map(DepartmentDTO::new).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}

		// 결과를 리턴
		resultMap.put("departmentList", departmentList);
		resultMap.put("message", message);
		resultMap.put("status", status);
		return resultMap;
	}

	/**
	 * 부서 등록 (CREATE)
	 * 
	 * @return 등록한 부서 정보
	 */
	@Transactional
	public Map<String, Object> create(@RequestBody DepartmentDTO departmentDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.CREATED;
		String message = ResponseCode.Message.CREATED;
		Department department = null;
		
		// 부서 등록 (CREATE)
		try {
			LocalDateTime now = LocalDateTime.now();
			departmentDTO.setRegistDate(now);
			if (departmentDTO.getUseYn() == null) {
				departmentDTO.setUseYn(1);
			}
			department = departmentRepository.save(departmentDTO.toEntity());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("department", department);
		return resultMap;
	}

	/**
	 * 부서 수정 (UPDATE)
	 * 
	 * @param DepartmentDTO, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> update(@RequestBody DepartmentDTO departmentDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		Department department = null;
		
		// 해당 부서가 있는지 확인
		try {
			departmentRepository.findById(departmentDTO.getId()).orElseThrow(() -> new IllegalArgumentException("해당 부서가 없습니다. id: " + departmentDTO.getId()));	
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.DEPARTMENT_NOT_FOUND;
			message = ResponseCode.Message.DEPARTMENT_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}

		// 부서 수정 (UPDATE)
		try {
			LocalDateTime now = LocalDateTime.now();
			departmentDTO.setUpdateDate(now);
			if (departmentDTO.getUseYn() == null) {
				departmentDTO.setUseYn(1);
			}
			department = departmentRepository.save(departmentDTO.toEntity());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("department", department);
		return resultMap;
	}

	/**
	 * 부서 삭제 (DELETE)
	 * 
	 * @return http 응답코드
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> param, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// 부서 삭제 (DELETE)
		try {
			for (int p = 0; p < param.size(); p++) {
				departmentRepository.deleteById(param.get(p));				
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
