package com.oms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 사이드바 메뉴
 * @author JSW
 *
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TB_MENU")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;																				// 메뉴 ID (기본키)

	@Column(nullable=false)
	@ColumnDefault("0")
	private Long depth;																		// 메뉴 단계
	
	@Column(nullable=false)
	private Long parentId;																	// 부모 메뉴 ID (부모 메뉴는 자기 자신의 ID로)

	@Column(length=8, nullable=false)
	private String name;																		// 메뉴 이름
	
	@Column(length=16)
	private String url;																			// 메뉴 URL

	@Column(length=32)
	private String icon;																			// 메뉴 아이콘

	@Column
	@Enumerated(EnumType.STRING)
	private UseYn useYn;																		// 사용여부
	
	@Column
	private String registUser;																// 등록자
	
	@Column
	private String updateUser;															// 수정자

	@Column(updatable=false)
	@CreatedDate
	private LocalDateTime registTime;											// 등록일시

	@Column
	@LastModifiedDate
	private LocalDateTime updateTime;											// 수정일시
	
	/**
	 * 메뉴 사용유무에 대한 정의
	 * @author JSW
	 */
	@Getter
	@RequiredArgsConstructor
	public enum UseYn {
		Y("Y", "사용"),
		N("N", "미사용");
		private final String key;
		private final String value;
	}
}
