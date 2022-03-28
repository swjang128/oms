package com.oms.dto;

import java.sql.Date;

import com.oms.entity.Position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
	private long id;					// ID
	private String name;			// 직급명		
	private int useYn;			// 사용여부	
	private Date registDate;	// 등록일자
	
	/**
	 * (Request) DTO -> Entity
	 * @return
	 */
	public Position toEntity() {
		return Position.builder()
				.id(id)
				.name(name)
				.useYn(useYn)
				.registDate(registDate)
				.build();
	}
	
	public PositionDTO(Position position) {
		this.id = position.getId();
		this.name = position.getName();
		this.useYn = position.getUseYn();
		this.registDate = (Date) position.getRegistDate();
	}
}