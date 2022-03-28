package com.oms.dto;

import java.sql.Date;

import com.oms.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
	private long id;					// ID
	private String name;			// 부서명		
	private int useYn;			// 사용여부	
	private Date registDate;	// 등록일자
	
	/**
	 * (Request) DTO -> Entity
	 * @return
	 */
	public Department toEntity() {
		return Department.builder()
				.id(id)
				.name(name)
				.useYn(useYn)
				.registDate(registDate)
				.build();
	}
	
	public DepartmentDTO(Department department) {
		this.id = department.getId();
		this.name = department.getName();
		this.useYn = department.getUseYn();
		this.registDate = (Date) department.getRegistDate();
	}
}