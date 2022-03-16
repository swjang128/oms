package com.oms;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.oms.admin.dto.MemberDTO;
import com.oms.admin.entity.Member;
import com.oms.admin.repository.MemberRepository;
import com.oms.admin.service.AccountService;
import com.oms.security.config.AccountRole;
import com.oms.security.config.AccountStatus;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@Slf4j
public class OmsApplication {
	@Value("${spring.profiles.active}")
	private String profile;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	AccountService accountService;

	public static void main(String[] args) {		
		SpringApplication.run(OmsApplication.class, args);		
	}
	/**
	 * 애플리케이션 실행 시 Member 테이블에 계정이 하나도 없으면 테스트용 계정을 생성
	 */
	@PostConstruct
	public void init() {
		List<Member> memberList = memberRepository.findAll();
		String initEmail = "admin";
		String initPassword = "admin";
		if (memberList.size() == 0) {
			log.info("****** 계정 정보가 존재하지 않습니다. 테스트용 계정을 생성합니다.");
			MemberDTO memberDTO = new MemberDTO();
			Date date = new Date();
			memberDTO.setEmail(initEmail);
			memberDTO.setPassword(initPassword);
			memberDTO.setName("admin");
			memberDTO.setBirthday(date);
			memberDTO.setHireDate(date);
			memberDTO.setPhone("01000000000");
			memberDTO.setEmergencyContact("01000000000");
			memberDTO.setPhoto("");
			memberDTO.setPosition("사원");
			memberDTO.setStatus(AccountStatus.ACTIVE);
			memberDTO.setRole(AccountRole.ADMIN);
			memberDTO.setDepartment("판매");
			
			accountService.create(memberDTO);
		}
		
		if (!"prod".equals(profile)) {
			log.info("************************************************************");
			log.info("****************** << 테스트용 계정 정보 >> *******************");
			log.info("********** [메일]: [{}] **********************************", initEmail);
			log.info("********** [암호]: [{}] **********************************", initPassword);
			log.info("************************************************************");
		}
	}

}
