package com.oms.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.oms.dto.AccountDTO;
import com.oms.entity.Account;
import com.oms.error.controller.ExceptionManager;
import com.oms.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 참조: https://velog.io/@ewan/Spring-security-success-failure-handler

/**
 * 로그인 실패 핸들러
 * 
 * @author sw.jang
 *
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	ExceptionManager exceptionManager;	
	private final AccountRepository accountRepository;
	
	/**
	 * 로그인 실패시 실행될 작업에 대한 메소드
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String result = exceptionManager.authenticationExceptionHandler(exception);
		String email = request.getParameter("email");
		// 로그인 실패 횟수가 5번 이상이 되면 계정을 BLOCKED 상태로 바꾸고 이외에는 로그인 실패 횟수 증가	
		try {
			Account account = accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다." + email));
			AccountDTO accountDTO = new AccountDTO(account);
			int failCount = account.getFailCount();
			if (failCount < 5) {
				accountDTO.setFailCount(failCount+1);
			} else {
				accountDTO.setStatus(Account.Status.BLOCKED);
			}
			accountDTO.setEmail(email);
			accountRepository.save(accountDTO.toEntity());
		} catch (Exception e) {	// 계정이 존재하지 않을 경우
			log.info("{}는 존재하지 않는 계정입니다.", email);
		} finally {	// 실패 정보를 파라미터로 넘겨서 완료			
			// 로그인 실패 후 이동할 URL
			setDefaultFailureUrl("/oms?result="+result);
			super.onAuthenticationFailure(request, response, exception);
		}
	}
}
