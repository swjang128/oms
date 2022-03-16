package com.oms.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사이드바 메뉴
 * @author Capias J
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TB_MENU")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;							// 메뉴 ID (기본키)

	@Column(nullable=false)
	private Long parentId;						// 부모 메뉴 ID (부모 메뉴는 값을 0으로 지정)

	@Column(length=16, nullable=false)
	private String name;						// 메뉴 이름

	@Column(length=32)
	private String icon;						// 메뉴 아이콘

}
