package com.capias.admin.dto;

import java.util.Date;

import com.capias.admin.entity.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
	private Long id;																					// 업무 ID
	private Long parentId;																		// 상위업무 ID
	private String title;																				// 업무 제목
	private String content;																		// 업무 내용
	private String type;																			// 업무 종류
	private String status;																			// 진행 상황
	private String register;																		// 등록자
	private String manager;																	// 담당자
	private String worker;																		// 작업자
	private Date registDate; 																	// 등록일자
	private Date updateDate; 																// 수정일자
	private Date startDate; 																	// 시작일자
	private Date endDate; 																		// 완료일자
	
	/**
	 * (Request) DTO -> Entity
	 * DTO를 Entity로 변환
	 * @return
	 */
	public Task toEntity() {
		Date date = new Date();
		return Task.builder()
				.id(id)
				.parentId(parentId)
				.title(title)
				.content(content)
				.type(type)
				.status(status)
				.register(register)
				.manager(manager)
				.worker(worker)
				.registDate(date)
				.updateDate(updateDate)
				.startDate(startDate)
				.endDate(endDate)
				.build();
	}
	
	public TaskDTO(Task task) {
		this.id = task.getId();
		this.parentId = task.getParentId();
		this.title = task.getTitle();
		this.content = task.getContent();
		this.type = task.getType();
		this.status = task.getStatus();
		this.register = task.getRegister();
		this.manager = task.getManager();
		this.worker = task.getWorker();
		this.registDate = task.getRegistDate();
		this.updateDate = task.getUpdateDate();
		this.startDate = task.getStartDate();
		this.endDate = task.getEndDate();
	}
}