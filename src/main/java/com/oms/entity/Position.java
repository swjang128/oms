package com.oms.entity;

import java.time.LocalDateTime;
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
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 직급
 * @author Capias J
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "TB_POSITION")
public class Position {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;														// ID

	@Column
	private String name;												// 직급이름

	@Column(length=1)
	@ColumnDefault("1")
	private Integer useYn;											// 사용여부

	@Column(updatable=false)
	@CreatedDate
	private LocalDateTime registDate;						// 등록일자

	@Column()
	@LastModifiedDate
	private LocalDateTime updateDate;					// 수정일자

}
