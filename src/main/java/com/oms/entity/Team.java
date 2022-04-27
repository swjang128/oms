package com.oms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 팀
 * @author JSW
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TB_TEAM")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;																				// 팀 ID (기본키)
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project_id")
	private Project project;																	// 진행중인 프로젝트 ID (N:1)
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="task_id")
	private Task task;																			// 진행중인 업무 ID (N:1)

	@Column(nullable=false, unique=true)
	private String name;																		// 팀명

	@Column()
	private String descryption;															// 팀설명

	@Column(updatable=false)
	private String registUser;																// 등록자

	@Column(insertable=false)
	private String updateUser;															// 수정자
	
	@Column(updatable=false)
	@CreatedDate
	private LocalDateTime registDate;												// 등록일시
	
	@Column
	@LastModifiedDate
	private LocalDateTime updateDate;											// 수정일시
}
