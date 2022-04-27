package com.oms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.oms.entity.Project;
import com.oms.entity.Task;
import com.oms.entity.Task.Category;
import com.oms.entity.Task.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
	private Long id;																					// 업무 ID (기본키)
	private Project project;																		// 진행중인 프로젝트 ID (N:1)
	private String name;																			// 업무명
	private String descryption;																// 업무설명
	private Category category;																// 분류
	private Status status;																			// 진행 상황
	private LocalDateTime registDate;												// 등록일시
	private String registUser;																// 등록자
	private LocalDateTime updateDate;											// 수정일시
	private String updateUser;															// 수정자
	private LocalDate startDate; 																	// 시작일자
	private LocalDate endDate; 																		// 완료일자
	
	/**
	 * (Request) DTO -> Entity
	 * DTO를 Entity로 변환
	 * @return
	 */
	public Task toEntity() {
		return Task.builder()
				.id(id)
				.project(project)
				.name(name)
				.descryption(descryption)
				.category(category)
				.status(status)
				.registDate(registDate)
				.registUser(registUser)
				.updateDate(updateDate)
				.updateUser(updateUser)
				.startDate(startDate)
				.endDate(endDate)
				.build();
	}
	
	public TaskDTO(Task task) {
		this.id = task.getId();
		this.project = task.getProject();
		this.name = task.getName();
		this.descryption = task.getDescryption();
		this.category = task.getCategory();
		this.status = task.getStatus();
		this.registDate = task.getRegistDate();
		this.registUser = task.getRegistUser();
		this.updateDate = task.getUpdateDate();
		this.updateUser = task.getUpdateUser();
		this.startDate = task.getStartDate();
		this.endDate = task.getEndDate();
	}
}