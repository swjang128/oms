package com.oms.dto;

import java.time.LocalDateTime;
import com.oms.entity.Project;
import com.oms.entity.Task;
import com.oms.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
	private Long id;																				// 팀 ID (기본키)
	private Project project;																	// 진행중인 프로젝트 ID (N:1)
	private Task task;																			// 진행중인 업무 ID (N:1)
	private String name;																		// 팀명
	private String descryption;															// 팀설명
	private String registUser;																// 등록자
	private String updateUser;															// 수정자
	private LocalDateTime registDate;												// 등록일시
	private LocalDateTime updateDate;											// 수정일시
	
	/**
	 * (Request) DTO -> Entity
	 * DTO를 Entity로 변환
	 * @return
	 */
	public Team toEntity() {
		return Team.builder()
				.id(id)
				.project(project)
				.task(task)
				.name(name)
				.descryption(descryption)
				.registUser(registUser)
				.updateUser(updateUser)
				.registDate(registDate)
				.updateDate(updateDate)
				.build();
	}
	
	public TeamDTO(Team team) {
		this.id = team.getId();
		this.project = team.getProject();
		this.task = team.getTask();
		this.name = team.getName();
		this.descryption = team.getDescryption();
		this.registUser = team.getRegistUser();
		this.updateUser = team.getUpdateUser();
		this.registDate = team.getRegistDate();
		this.updateDate = team.getUpdateDate();
	}
}