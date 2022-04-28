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
	private UseYn useYn;											// 사용여부
	private String registUser;									// 등록자
	private String updateUser;								// 수정자
	private LocalDateTime registTime;				// 등록일시
	private LocalDateTime updateTime;				// 수정일시

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
				.registUser(registUser)
				.updateUser(updateUser)
				.registTime(registTime)
				.updateTime(updateTime)
				.build();
	}

	public PositionDTO(Position position) {
		this.id = position.getId();
		this.name = position.getName();
		this.useYn = position.getUseYn();
		this.registUser = position.getRegistUser();
		this.updateUser = position.getUpdateUser();
		this.registTime = position.getRegistTime();
		this.updateTime = position.getUpdateTime();
	}
}