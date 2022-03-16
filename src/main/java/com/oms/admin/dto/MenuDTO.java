package com.oms.admin.dto;

import com.oms.admin.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
	private Long id;							// 메뉴 ID (기본키)
	private Long parentId;						// 부모 메뉴 ID (부모 메뉴는 값을 0으로 지정)
	private String name;						// 메뉴 이름
	private String icon;						// 메뉴 아이콘
	
	/**
	 * (Request) DTO -> Entity
	 * DTO를 Entity로 변환
	 * @return
	 */
	public Menu toEntity() {
		return Menu.builder()
				.id(id)
				.parentId(parentId)
				.name(name)
				.icon(icon)
				.build();
	}
	
	public MenuDTO(Menu menu) {
		this.id = menu.getId();
		this.parentId = menu.getParentId();
		this.name = menu.getName();
		this.icon = menu.getIcon();
	}
}