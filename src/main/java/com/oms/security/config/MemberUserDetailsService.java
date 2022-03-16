package com.oms.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.oms.admin.entity.Member;
import com.oms.admin.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class MemberUserDetailsService implements UserDetailsService {	
	@Autowired
	MemberRepository memberRepository;

	/**
	 * 해당 유저가 있는지 DB에서 확인
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("****** 받아온 email: {}", email);
		// 해당 유저가 있는지 확인. 있다면 member를 리턴해주고 없다면 Throw Exception
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. " + email));
		
		return new MemberUserDetails(member);
	}

}
