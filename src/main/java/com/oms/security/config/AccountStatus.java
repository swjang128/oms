package com.oms.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Member 테이블에 대한 권한 정의
 * @author user
 *
 */
@Getter
@RequiredArgsConstructor
public enum AccountStatus {
	ACTIVE("ACTIVE", "활성화"),
	BLOCKED("BLOCKED", "잠김"),
	EXPIRED("EXPIRED", "만료됨");
	
	private final String key;
	private final String value;
}
