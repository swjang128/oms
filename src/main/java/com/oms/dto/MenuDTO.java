package com.oms.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.oms.entity.Menu;
import com.oms.entity.Menu.UseYn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
	private Long id;											// 메뉴 ID (기본키)
	private Long depth;									// 메뉴 단계
	private List<MenuDTO> children;			// 하위 메뉴
	private String name;									// 메뉴 이름
	private String url;										// 메뉴 URL
	private String icon;										// 메뉴 아이콘
	private UseYn useYn;									// 사용여부
	private String registUser;							// 등록자
	private String updateUser;						// 수정자
	private LocalDateTime registTime;		// 등록일시
	private LocalDateTime updateTime;		// 수정일시
	
	/**
	 * (Request) DTO -> Entity
	 * DTO를 Entity로 변환
	 * @return
	 */
	public Menu toEntity() {
		return Menu.builder()
				.id(id)
				.depth(depth)				
				.name(name)
				.url(url)
				.icon(icon)
				.useYn(useYn)
				.registUser(registUser)
				.updateUser(updateUser)
				.registTime(registTime)
				.updateTime(updateTime)
				.build();
	}
	
	public MenuDTO(Menu menu) {
		this.id = menu.getId();
		this.depth = menu.getDepth();
		this.children = menu.getChildren().stream().map(MenuDTO::new).collect(Collectors.toList());
		this.name = menu.getName();
		this.url = menu.getUrl();
		this.icon = menu.getIcon();
		this.useYn = menu.getUseYn();
		this.registUser = menu.getRegistUser();
		this.updateUser = menu.getUpdateUser();
		this.registTime = menu.getRegistTime();
		this.updateTime = menu.getUpdateTime();
	}
}