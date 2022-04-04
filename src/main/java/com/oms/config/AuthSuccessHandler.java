package com.oms.config;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.oms.dto.AccountDTO;
import com.oms.entity.Account;
import com.oms.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 성공 핸들러
 * @author capias.J
 *
 */
@RequiredArgsConstructor
@Component(value="authenticationSuccessHandler")
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	ModelMapper modelMapper;
	
	private final AccountRepository memberRepository;	
	private final HttpSession session;

	/**
	 * 로그인 성공시 실행하는 작업들
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("****** 로그인 시작 ");
		// Member 객체 생성
		Account member = memberRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 계정입니다." + authentication.getName()));
		// 로그인한 계정의 상태가 ACTIVE가 아니면 계정이 잠겼다고 LockedException 예외 throw
		if (!"ACTIVE".equals(member.getStatus().getKey())) {
			log.info("member 상태: {}", member.getStatus().getKey());
			throw new LockedException("");
		}
		
		// 로그인하면 현재 시간으로 lastLogin 시간 변경
		memberRepository.updateMemberLastLogin(authentication.getName(), LocalDateTime.now());
		
		// 비밀번호 틀린 횟수 초기화
		memberRepository.updateFailCountReset(authentication.getName());
		
		// 로그인한 사용자 정보를 조회하여 세션에 담기
		AccountDTO memberDTO = new AccountDTO(member);
		session.setAttribute("id", memberDTO.getId());
		session.setAttribute("department", memberDTO.getDepartment());
		session.setAttribute("position", memberDTO.getPosition());
		session.setAttribute("photo", memberDTO.getPhoto());
		session.setAttribute("name", memberDTO.getName());
		session.setAttribute("email", memberDTO.getEmail());
		session.setAttribute("password",  memberDTO.getPassword());
		session.setAttribute("role",  memberDTO.getRole());
		session.setAttribute("phone", memberDTO.getPhone());
		session.setAttribute("emergencyContact", memberDTO.getEmergencyContact());
		session.setAttribute("birthday", memberDTO.getBirthday());
		session.setAttribute("hireDate", memberDTO.getHireDate());
		
		//  모든 작업이 끝나고 이동할 url 설정
		setDefaultTargetUrl("/oms/project");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
