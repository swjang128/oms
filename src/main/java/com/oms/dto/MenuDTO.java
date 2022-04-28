package com.oms.dto;

import java.time.LocalDateTime;

import com.oms.entity.Menu;
import com.oms.entity.Menu.UseYn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
	private Long id;							// 메뉴 ID (기본키)
	private Long depth;					// 메뉴 단계
	private Long parentId;						// 부모 메뉴 ID (부모 메뉴는 값을 0으로 지정)
	private String name;						// 메뉴 이름
	private String domain;				// 메뉴 도메인
	private String icon;						// 메뉴 아이콘
	private UseYn useYn;					// 사용여부
	private String registUser;			// 등록자
	private String updateUser;			// 등록자
	private LocalDateTime registDate;			// 등록자
	private LocalDateTime updateDate;			// 등록자
	
	/**
	 * (Request) DTO -> Entity
	 * DTO를 Entity로 변환
	 * @return
	 */
	public Menu toEntity() {
		return Menu.builder()
				.id(id)
				.depth(depth)
				.parentId(parentId)				
				.name(name)
				.domain(domain)
				.icon(icon)
				.useYn(useYn)
				.registUser(registUser)
				.updateUser(updateUser)
				.registDate(registDate)
				.updateDate(updateDate)
				.build();
	}
	
	public MenuDTO(Menu menu) {
		this.id = menu.getId();
		this.depth = menu.getDepth();
		this.parentId = menu.getParentId();
		this.name = menu.getName();
		this.domain = menu.getDomain();
		this.icon = menu.getIcon();
		this.useYn = menu.getUseYn();
		this.registUser = menu.getRegistUser();
		this.updateUser = menu.getUpdateUser();
		this.registDate = menu.getRegistDate();
		this.updateDate = menu.getUpdateDate();
	}
}