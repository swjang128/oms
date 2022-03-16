package com.oms.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 업무
 * @author Capias J
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TB_TASK")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;																					// 업무 ID (기본키)

	@Column()
	@ColumnDefault("0")
	private Long parentId;																		// 상위업무 ID (최상위 업무는 값을 0으로 지정)

	@Column()
	private String title;																				// 업무 제목
	
	@Column()
	private String content;																		// 업무 내용

	@Column()
	private String type;																			// 업무 종류 (ex. 신규개발 / 기능수정 / 기능제거 등등) (추가로 테이블을 생성해서 관리할지 추후 논의)

	@Column()
	private String status;																			// 진행 상황 (ex. 준비 / 진행 / 완료) (추가로 테이블을 생성해서 관리할지 추후 논의)
	
	@Column()
	private String register;																		// 등록자 (이름으로 표시)
	
	@Column()
	private String manager;																	// 담당자 (업무 담당자)
	
	@Column()
	private String worker;																		// 작업자 (업무를 실제로 하는 사람)
	
	@Column(updatable=false)
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date registDate; 																	// 등록일자
	
	@Column(updatable=false)
	@Temporal(TemporalType.DATE)
	private Date updateDate; 																// 수정일자
	
	@Column(updatable=false)	
	@Temporal(TemporalType.DATE)
	private Date startDate; 																	// 시작일자
	
	@Column(updatable=false)	
	@Temporal(TemporalType.DATE)
	private Date endDate; 																		// 완료일자
	
}
