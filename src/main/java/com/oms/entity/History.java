package com.oms.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 접속로그
 * @author jsw
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TB_HISTORY")
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;																				// 접속로그 ID (기본키)

	@Column()
	private String host;																			// 요청자

	@Column(nullable=false)
	private String clientIp;																	// 클라이언트 IP
	
	@Column()
	private String requestUri;																// 요청한 URI
	
	@Column()
	private String method;																	// 요청 메소드
	
	@Column()
	private Integer status;																	// 응답코드
	
	@Column(updatable=false)
	@CreatedDate
	private LocalDateTime requestTime; 										// 호출일시
}
