package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oms.config.ResponseCode;
import com.oms.dto.DepartmentDTO;
import com.oms.entity.Department;
import com.oms.repository.DepartmentRepository;
import com.oms.specification.DepartmentSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
public class DepartmentService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	DepartmentRepository departmentRepository;

	/**
	 * 부서 등록 (CREATE)
	 * 
	 * @return 등록한 부서 정보
	 */
	@Transactional
	public Map<String, Object> create(DepartmentDTO departmentDTO, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.CREATED;
		Department department = null;
		// 부서 등록 (CREATE)
		try {
			department = departmentRepository.save(departmentDTO.toEntity());
			resultMap.put("department", department);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}

	/**
	 * 부서 조회 (READ)
	 * 
	 * @param Map<String, Object>, Map<String, Object>
	 * @return List<Department>
	 */
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
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
			departmentDTO = department.stream().map(d -> modelMapper.map(d, DepartmentDTO.class))
					.collect(Collectors.toList());
			resultMap.put("departmentList", departmentDTO);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}

	/**
	 * 부서 개수 (READ)
	 * 
	 * @param Map<String, Object>, Map<String, Object>
	 * @return Integer
	 */
	public Map<String, Object> count(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		// 기본 변수 설정
		ResponseCode result = ResponseCode.SUCCESS;
		long count = 0;
		Object useYn = paramMap.get("useYn");
		Object name = paramMap.get("name");
		Specification<Department> specification = (root, query, criteriaBuilder) -> null;
		// Specification 조건 작성
		if (useYn != null)
			specification = specification.and(DepartmentSpecification.findByUseYn(useYn));
		if (name != null) {
			specification = specification.and(DepartmentSpecification.findByName(name));
		}
		// Specification 조건에 맞는 부서 개수 조회
		try {
			count = departmentRepository.count(specification);
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
	 * 부서 수정 (UPDATE)
	 * 
	 * @param DepartmentDTO, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> update(DepartmentDTO departmentDTO, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
		// 해당 부서가 있는지 확인
		try {
			departmentRepository.findById(departmentDTO.getId())
					.orElseThrow(() -> new IllegalArgumentException("해당 부서가 없습니다. id: " + departmentDTO.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.DEPARTMENT_DOES_NOT_EXISTS;
			resultMap.put("result", result);
			return resultMap;
		}
		// 부서 수정 (UPDATE)
		try {
			departmentRepository.save(departmentDTO.toEntity());
			resultMap.put("department", departmentDTO);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}

	/**
	 * 부서 삭제 (DELETE)
	 * 
	 * @return http 응답코드
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> param, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
		// 부서 삭제 (DELETE)
		try {
			for (int p = 0; p < param.size(); p++) {
				departmentRepository.deleteById(param.get(p));
			}
			resultMap.put("deleteDepartment", param);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}

}
