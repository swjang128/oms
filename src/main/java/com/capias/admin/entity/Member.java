package com.capias.admin.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.capias.security.config.AccountRole;
import com.capias.security.config.AccountStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자
 * @author Capias J
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@EqualsAndHashCode(of="id")
@Table(name="TB_MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;									// 사번
	
	@Column(length=16, updatable=false)
	private String department;							// 부서
	
	@Column(length=16, updatable=false)
	private String position;							// 직책
	
	@Column(length=1024, updatable=false)
	private String photo;								// 사진
	
	@Column(length=64, updatable=false)
	private String name;				 				// 이름

	@Column(length=64, nullable=false, unique=true)
    private String email;								// 이메일
	
	@Column(length=64, insertable=true)
	private String password;							// 비밀번호
	
	@Column(length=64)
	private int failCount;								// 비밀번호 틀린 횟수
	
	@Column(length=8, insertable=true)
	@Enumerated(EnumType.STRING)
	private AccountStatus status;								// 상태
	
	@Column(length=8, insertable=true, updatable=false)
	@Enumerated(EnumType.STRING)
	private AccountRole role;									// 권한
	
	@Column(length=16, insertable=true, updatable=false)
    private String phone;								// 연락처
	
	@Column(length=16, insertable=true, updatable=false)
	private String emergencyContact;					// 비상연락처
	
	@Temporal(TemporalType.DATE)
	@Column(updatable=false)
	private Date birthday;								// 생일
	
	@Temporal(TemporalType.DATE)
	@Column(insertable=true, updatable=false)
	private Date hireDate;								// 입사일
	
	@Column(updatable=false)
	private LocalDateTime lastLoginTime;				// 마지막 로그인 시간
	
}


