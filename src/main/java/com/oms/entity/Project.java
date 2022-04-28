package com.oms.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 * 업무
 * @author Capias J
 *
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TB_PROJECT")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;																					// 프로젝트 ID (기본키)

	@Column()
	private String name;																			// 프로젝트명
	
	@Column()
	private String description;																// 설명

	@Column()
	private String department;																// 부서

	@Column()
	private String manager;																	// PM
	
	@Column()
	private String status;																			// 상태 (ready, processing, complete)
	
	@Column(updatable=false)
	private String registUser;																// 등록자
	
	@Column(insertable=false)
	private String updateUser;															// 수정자

	@Column(updatable=false)
	@CreatedDate
	private LocalDateTime registTime;												// 등록일시
	
	@Column
	@LastModifiedDate
	private LocalDateTime updateTime;											// 수정일시
	
	@Column()
	private LocalDate startDate; 														// 시작일
	
	@Column()
	private LocalDate endDate; 														// 완료일
	
}
