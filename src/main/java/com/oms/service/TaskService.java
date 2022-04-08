package com.oms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.dto.TaskDTO;
import com.oms.entity.Task;
import com.oms.repository.TaskRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 업무 목록을 관리하는 Service
 * @author Capias J
 *
 */
@Service
@Slf4j
public class TaskService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	TaskRepository taskRepository;

	/** 
	 * 업무 목록 조회 (READ)
	 * @return List<TaskDTO>
	 */
	public List<TaskDTO> read() {
		// 업무 목록 조회
		List<Task> taskList = taskRepository.findAll();
		List<TaskDTO> result = taskList.stream()											 
									 .map(TaskDTO::new)
									 .collect(Collectors.toList());
		
		log.info("###### TaskList Result: {}", result);
		
		return result;
	}
	
	/** 
	 * 상위 업무 조회 (READ)
	 * 메인 업무는 parentId를 0으로 받아서 조회한다
	 * @return List<TaskDTO>
	 */
//	public List<TaskDTO> readParent(Long taskId) {		
//		// 업무(대분류) 목록 조회
//		List<Task> parentTask = taskRepository.findByParentId(taskId);
//		List<TaskDTO> result = parentTask.stream()											 
//				 .map(TaskDTO::new)
//				 .collect(Collectors.toList());
//		log.info("****** 소분류 업무: {}", result);
//		
//		return result;
//	}
	
	/** 
	 * 특정 ParentId를 가진 업무 조회 (READ)
	 * @return List<TaskDTO>
	 */
	public List<TaskDTO> read(Long taskId) {		
		// 업무(대분류) 목록 조회
		List<Task> parentTask = taskRepository.findByParentId(taskId);
		List<TaskDTO> result = parentTask.stream()											 
				 .map(TaskDTO::new)
				 .collect(Collectors.toList());
		log.info("****** 소분류 업무: {}", result);
		
		return result;
	}
	
	/** 
	 * 업무 등록 (CREATE)
	 * @return 등록한 업무 정보
	 */
	@Transactional
	public Task create(@RequestBody TaskDTO taskDTO) {
		// 업무 등록 (CREATE)
		Task result = taskRepository.save(taskDTO.toEntity());

		return result;
	}
	
	/** 
	 * 업무 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Integer update(@RequestBody TaskDTO taskDTO) {
		Long id = taskDTO.getId();		
		int result = 0;
		// 해당 업무이 있는지 확인
		taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 업무이 없습니다. id: "+id));
		
		try {
			// 업무 수정 (UPDATE)
			taskRepository.save(taskDTO.toEntity());
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * 업무 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> param) {
		int result = 0;
		
		// 업무 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				taskRepository.deleteById(param.get(p));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
