package com.oms.config;

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
	USER("ROLE_USER", "직원"),
	MANAGER("ROLE_MANAGER", "팀장"),
	ADMIN("ROLE_ADMIN", "관리자");
	
	private final String key;
	private final String value;
}
