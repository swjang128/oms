package com.capias.admin.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.springframework.data.annotation.CreatedDate;

import com.capias.admin.entity.History;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {
	private Long id;							// 접속로그 ID (기본키)
	private String host;						// 요청 유저(로그인한 상태라면 계정 email, 세션이 없으면 null)
	private String clientIp;						// 클라이언트 IP
	private String requestUri;						// 요청한 URI
	private String method;						// 요청 메소드
	private int status;					// 응답코드
	private LocalDateTime requestDate;					// 요청한 시간
	
	/**
	 * (Request) DTO -> Entity
	 * HistoryDTO를 Entity로 변환
	 * @return
	 */
	public History toEntity() {
		return History.builder()
				.id(id)
				.host(host)
				.clientIp(clientIp)
				.requestUri(requestUri)
				.method(method)
				.status(status)
				.requestDate(requestDate)
				.build();
	}
	
	public HistoryDTO(History history) {
		this.id = history.getId();
		this.host = history.getHost();
		this.clientIp = history.getClientIp();
		this.requestUri = history.getRequestUri();
		this.method = history.getMethod();
		this.status = history.getStatus();
		this.requestDate = history.getRequestDate();
	}
}