package com.oms.entity;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TB_PROJECT")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;																					// 프로젝트 ID (기본키)

	@Column()
	private String title;																				// 제목
	
	@Column()
	private String description;																// 설명

	@Column()
	private String department;																// 부서

	@Column()
	private String manager;																	// PM
	
	@Column()
	private String status;																			// 상태 (ready, processing, complete)
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumn(name="account_id")
	private Account account;																// 구성원
	
	@Column(updatable=false)
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date registDate; 																	// 등록일
	
	@Column(updatable=false)
	@LastModifiedDate
	@Temporal(TemporalType.DATE)
	private Date updateDate; 																// 수정일
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date startDate; 																	// 시작일
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date endDate; 																		// 완료일
	
}
