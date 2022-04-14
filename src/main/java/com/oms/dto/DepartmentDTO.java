package com.oms.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.oms.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
	private long id;															// ID
	private String name;													// 부서명
	private Integer useYn;												// 사용여부	
	private LocalDateTime registDate;							// 등록일자
	private LocalDateTime updateDate;						// 등록일자
	
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
				.updateDate(updateDate)
				.build();
	}
	
	public DepartmentDTO(Department department) {
		this.id = department.getId();
		this.name = department.getName();
		this.useYn = department.getUseYn();
		this.registDate = department.getRegistDate();
		this.updateDate = department.getUpdateDate();
	}
}
