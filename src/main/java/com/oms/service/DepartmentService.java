package com.oms.service;

import java.time.LocalDateTime;
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
import com.oms.dto.DepartmentDTO;
import com.oms.entity.Department;
import com.oms.entity.Department.UseYn;
import com.oms.repository.DepartmentRepository;
import com.oms.specification.DepartmentSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	DepartmentRepository departmentRepository;

	/**
	 * 부서 조회 (READ)
	 * 
	 * @return List<Department>
	 */
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Department> department = new ArrayList<Department>();
		List<DepartmentDTO> departmentDTO = new ArrayList<DepartmentDTO>();
		Object useYn = paramMap.get("useYn");
		Object name = paramMap.get("name");
		Specification<Department> specification = (root, query, criteriaBuilder) -> null;
		// 부서 목록 조회
		try {
			if (useYn != null)
				specification = specification.and(DepartmentSpecification.findByUseYn(useYn));
			if (name != null)
				specification = specification.and(DepartmentSpecification.findByName(name));
			department = departmentRepository.findAll(specification);
			departmentDTO = department.stream().map(d -> modelMapper.map(d, DepartmentDTO.class)).collect(Collectors.toList());
			resultMap.put("departmentList", departmentDTO);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
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
				departmentDTO.setUseYn(UseYn.Y);
			}
			department = departmentRepository.save(departmentDTO.toEntity());
			resultMap.put("department", department);
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
	 * 부서 수정 (UPDATE)
	 * 
	 * @param DepartmentDTO, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> update(@RequestBody DepartmentDTO departmentDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
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
			if (departmentDTO.getUseYn()!=UseYn.Y && departmentDTO.getUseYn()!=UseYn.N) {
				departmentDTO.setUseYn(UseYn.Y);
			}
			departmentRepository.save(departmentDTO.toEntity());
			resultMap.put("department", departmentDTO);
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
			resultMap.put("deleteDepartment", param);
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
