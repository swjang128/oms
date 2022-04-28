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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 부서
 * @author jsw
 *
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "TB_DEPARTMENT")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;																				// ID

	@Column
	private String name;																		// 부서이름

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
	 * 부서 사용유무에 대한 정의
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
