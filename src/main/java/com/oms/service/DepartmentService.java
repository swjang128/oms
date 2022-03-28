package com.oms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.dto.DepartmentDTO;
import com.oms.entity.Department;
import com.oms.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	DepartmentRepository departmentRepository;
		
	/** 
	 * 부서 목록 조회 (READ)
	 * @return List<Department>
	 */
	public List<DepartmentDTO> read(int useYn) {
		// 부서 목록 조회 (사용하는 부서만)
		//useYn = 1;
		List<Department> DepartmentList = departmentRepository.findByUseYn(useYn);
		List<DepartmentDTO> result = DepartmentList.stream()											 
									 .map(DepartmentDTO::new)
									 .collect(Collectors.toList());
		
		log.info("###### DepartmentList Result: {}", result);
		
		return result;
	}
	
	/** 
	 * 부서 등록 (CREATE)
	 * @return 등록한 부서 정보
	 */
	@Transactional
	public Department create(@RequestBody DepartmentDTO departmentDTO) {
		// 부서 등록 (CREATE)
		Department result = departmentRepository.save(departmentDTO.toEntity());

		return result;
	}
	
	/** 
	 * 부서 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Integer update(@RequestBody DepartmentDTO departmentDTO) {
		Long id = departmentDTO.getId();		
		int result = 0;
		// 해당 부서이 있는지 확인
		departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 부서이 없습니다. id: "+id));
		
		try {
			// 부서 수정 (UPDATE)
			departmentRepository.save(departmentDTO.toEntity());
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * 부서 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> param) {
		int result = 0;
		
		// 부서 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				departmentRepository.deleteById(param.get(p));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
