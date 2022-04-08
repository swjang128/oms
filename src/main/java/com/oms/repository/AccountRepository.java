package com.oms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oms.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	/*********************** JPA Native Queries ***********************/
	static final String UPDATE_ALL_USER_STATUS = """
													UPDATE TB_ACCOUNT
													SET USER_STATUS = :userStatus
												   """;
	
	Optional<Account> findByEmail(String email);	// 해당 유저가 있는지 확인
	boolean existsByEmail(String email);	// 해당 email을 가진 유저가 있는지 확인
	
	/**
	 * 모든 유저의 UserStatus를 변경
	 * @param enum UserStatus
	 */
	@Transactional
	@Modifying
	@Query(value=UPDATE_ALL_USER_STATUS, nativeQuery=true)
	public void updateAllUserStatus(@Param("userStatus") String userStatus);
}
