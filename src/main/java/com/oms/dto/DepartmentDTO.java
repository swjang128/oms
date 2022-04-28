package com.oms.dto;

import java.time.LocalDateTime;

import com.oms.entity.Department;
import com.oms.entity.Department.UseYn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
	private long id;															// ID
	private String name;													// 부서명
	private UseYn useYn;													// 사용여부
	private String registUser;											// 등록자
	private String updateUser;										// 수정자
	private LocalDateTime registTime;						// 등록일시
	private LocalDateTime updateTime;						// 수정일시
	
	/**
	 * (Request) DTO -> Entity
	 * @return
	 */
	public Department toEntity() {
		return Department.builder()
				.id(id)
				.name(name)
				.useYn(useYn)
				.registUser(registUser)
				.updateUser(updateUser)
				.registTime(registTime)
				.updateTime(updateTime)
				.build();
	}
	
	public DepartmentDTO(Department department) {
		this.id = department.getId();
		this.name = department.getName();
		this.useYn = department.getUseYn();
		this.registUser = department.getRegistUser();
		this.updateUser = department.getUpdateUser();
		this.registTime = department.getRegistTime();
		this.updateTime = department.getUpdateTime();
	}
}
