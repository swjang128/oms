package com.capias.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Member 테이블에 대한 권한 정의
 * @author user
 *
 */
@Getter
@RequiredArgsConstructor
public enum AccountRole {
	USER("ROLE_USER", "사용자"),
	MANAGER("ROLE_MANAGER", "직원"),
	ADMIN("ROLE_ADMIN", "관리자");
	
	private final String key;
	private final String value;
}
