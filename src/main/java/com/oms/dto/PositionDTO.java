package com.oms.dto;

import java.time.LocalDateTime;

import com.oms.entity.Position;
import com.oms.entity.Position.UseYn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
	private long id;													// ID
	private String name;											// 직급명
	private UseYn useYn;													// 사용여부
	private LocalDateTime registDate;					// 등록일자
	private LocalDateTime updateDate;				// 수정일자

	/**
	 * (Request) DTO -> Entity
	 * 
	 * @return
	 */
	public Position toEntity() {
		return Position.builder(	)
				.id(id)
				.name(name)
				.useYn(useYn)
				.registDate(registDate)
				.updateDate(updateDate)
				.build();
	}

	public PositionDTO(Position position) {
		this.id = position.getId();
		this.name = position.getName();
		this.useYn = position.getUseYn();
		this.registDate = position.getRegistDate();
		this.updateDate = position.getUpdateDate();
	}
}