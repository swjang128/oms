package com.oms.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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

	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;																		// 진행중인 프로젝트 ID (N:1)

	@Column()
	private String name;																			// 업무명
	
	@Column()
	private String descryption;																// 업무설명

	@Column()
	@Enumerated(EnumType.STRING)
	private Category category;																// 분류

	@Column()
	private Status status;																			// 진행 상황
	
	@Column(updatable=false)
	@CreatedDate
	private LocalDateTime registDate;												// 등록일시
	
	@Column(updatable=false)
	private String registUser;																// 등록자

	@Column
	@LastModifiedDate
	private LocalDateTime updateDate;											// 수정일시
	
	@Column(insertable=false)
	private String updateUser;															// 수정자
	
	@Column(updatable=false)	
	private LocalDate startDate; 														// 시작일자
	
	@Column(updatable=false)	
	private LocalDate endDate; 														// 완료일자
	
	
	/**
	 * 업무의 카테고리 정의 enum
	 * @author JSW
	 */
	@Getter
	@RequiredArgsConstructor
	public enum Category {
		DEV("Develop", "개발"),
		OPS("Operations", "운영"),
		DEVOPS("DevOps", "개발운영"),
		PLAN("Planning", "기획"),
		BIC("BIC", "신규사업"),
		INVEST("Investment", "투자"),
		SHOP("Shop", "매장"),
		SALES("Sales", "영업"),
		PR("Promotion", "홍보"),
		DESIGN("Design", "디자인"),
		HR("HumanResource", "인사"),
		INFRA("Infrastructure", "인프라"),
		ETC("ETC", "기타");
		private final String english;
		private final String korean;
	}
	
	/**
	 * 업무의 상태 정의 enum
	 * @author JSW
	 */
	@Getter
	@RequiredArgsConstructor
	public enum Status {
		TODO("ToDo", "할일"),
		INPROGRESS("InProgress", "진행중"),
		REVIEW("Review", "검토중"),
		DONE("Done", "완료");
		private final String english;
		private final String korean;
	}
}
