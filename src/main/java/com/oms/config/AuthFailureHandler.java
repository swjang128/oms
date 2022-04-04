package com.oms.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.oms.entity.Account;
import com.oms.error.controller.ExceptionManager;
import com.oms.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 참조: https://velog.io/@ewan/Spring-security-success-failure-handler

/**
 * 로그인 실패 핸들러
 * 
 * @author capias.J
 *
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	ExceptionManager exceptionManager;	
	
	private final AccountRepository memberRepository;
	
	/**
	 * 로그인 실패시 실행될 작업에 대한 메소드
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String email = request.getParameter("email");
		log.info("**** 로그인했던 아이디: {}", email);
		
		// 로그인 실패 횟수가 5번 이상이 되면 계정을 잠금(BLOCKED) 상태로 바꾸고 이외에는 로그인 실패 횟수 증가	
		try {
			Optional<Account> member = memberRepository.findByEmail(request.getParameter("email"));
			int failCount = member.get().getFailCount();
			if (failCount < 5) {
				memberRepository.updateFailCount(email);
				log.info("**** 로그인 실패 횟수: {}", failCount);
			} else {
				memberRepository.updateStatus(email, AccountStatus.BLOCKED.getKey());
				log.info("**** 계정 잠김!");
			}
		} catch (Exception e) {	// 계정이 존재하지 않을 경우
			log.info("{}는 존재하지 않는 계정입니다.", email);
		} finally {	// 실패 정보를 파라미터로 넘겨서 완료
			// Exception Manager에서 계정 관련 예외처리 메시지를 리턴하는 메소드를 호출
			String result = exceptionManager.authenticationExceptionHandler(exception);
			// 로그인 실패 후 이동할 URL
			setDefaultFailureUrl("/oms?result="+result);
				
			super.onAuthenticationFailure(request, response, exception);
		}
		
	}
	
}
