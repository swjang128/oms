package com.capias.admin.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.capias.admin.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	/*********************** JPA Native Queries ***********************/
	static final String UPDATE_MEMBER_LAST_LOGIN = """
													UPDATE TB_MEMBER
													SET LAST_LOGIN_TIME = :lastLoginTime
													WHERE EMAIL = :email
												   """;
	static final String UPDATE_FAIL_COUNT = """
											UPDATE TB_MEMBER
											SET FAIL_COUNT = FAIL_COUNT + 1
											WHERE EMAIL = :email
											""";
	static final String UPDATE_FAIL_COUNT_RESET = """
													UPDATE TB_MEMBER
													SET FAIL_COUNT = 0
													WHERE EMAIL = :email
													""";
	static final String UPDATE_STATUS = """
										UPDATE TB_MEMBER
										SET STATUS = :status
										WHERE EMAIL = :email
										""";
	/*********************** JPA Interfaces ***********************/
	/**
	 * 사용자가 로그인하면 마지막 로그인 시간 값을 UPDATE
	 * @param email
	 * @param lastLoginTime
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value=UPDATE_MEMBER_LAST_LOGIN, nativeQuery=true)
	public void updateMemberLastLogin(@Param("email") String email, @Param("lastLoginTime") LocalDateTime lastLoginTime);
	
	/**
	 * 로그인 실패시 FAIL_COUNT를 증가
	 * @param email
	 * @param lastLoginTime
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value=UPDATE_FAIL_COUNT, nativeQuery=true)
	public void updateFailCount(@Param("email") String email);
	
	/**
	 * 계정 상태가 ACTIVE 일 때 로그인 성공하면 FAIL_COUNT를 0으로 초기화
	 * @param email
	 * @param lastLoginTime
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value=UPDATE_FAIL_COUNT_RESET, nativeQuery=true)
	public void updateFailCountReset(@Param("email") String email);
	
	/**
	 * 계정을 잠금 처리
	 * @param email
	 * @param lastLoginTime
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value=UPDATE_STATUS, nativeQuery=true)
	public void updateStatus(@Param("email") String email, @Param("status") String status);
	
	Optional<Member> findByEmail(String email);	// 해당 유저가 있는지 확인
	
	boolean existsByEmail(String email);	// 해당 email을 가진 유저가 있는지 확인
	boolean existsByPassword(String password);	// 비밀번호 체크
	
}
