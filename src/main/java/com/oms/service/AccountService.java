package com.oms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.config.AccountRole;
import com.oms.config.AccountStatus;
import com.oms.config.ResponseCode;
import com.oms.dto.MailDTO;
import com.oms.dto.AccountDTO;
import com.oms.entity.Account;
import com.oms.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String FROM_ADDRESS;

	/**
	 * 이메일 중복 체크
	 * @param email
	 * @return status
	 */
	public int checkEmail(String email) {
		int status = ResponseCode.Status.OK;
		if (accountRepository.existsByEmail(email)) {
			status = ResponseCode.Status.ACCOUNT_DUPLICATE; 
		};
		
		return status;
	}
	
	/**
	 * 직원 등록
	 * @param accountDTO
	 * @return
	 */
	@Transactional
	public int create(AccountDTO accountDTO) {		
		int status = ResponseCode.Status.OK;
		// 임시 Role은 Admin으로 Set, Status는 Active로 Set
		if ("admin@oms.com".equals(accountDTO.getEmail())) {
			accountDTO.setRole(AccountRole.ADMIN);				
		}
		accountDTO.setStatus(AccountStatus.ACTIVE);
		// 입력받아온 비밀번호는 암호화
		accountDTO.setPassword(encoder.encode(accountDTO.getPassword()));
		// accountDTO를 Entity로 변환
		Account account = accountDTO.toEntity();
		// 회원 가입 진행
		try {
			accountRepository.save(account);	
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.INTERNAL_SERVER_ERROR;
		}
		
		return status;
	}
	
	
	/** 
	 * 직원 목록 조회 (READ)
	 * @return List<Account>
	 */
	public List<AccountDTO> read() {
		// 직원 목록 조회
		List<Account> accountList = accountRepository.findAll();
		List<AccountDTO> result = accountList.stream()											 
											.map(account -> modelMapper.map(account, AccountDTO.class))
											.collect(Collectors.toList());

		return result;
	}
	
	/** 
	 * 직원 정보 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Integer update(AccountDTO accountDTO) {
		int result = 0;
		Long id = accountDTO.getId();
		// 직원 정보 수정 (UPDATE)
		try {
			// 해당 직원이 있는지 확인
			accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 직원 정보가 없습니다. id: "+id));
			// 있으면 UPDATE 실행
			accountRepository.save(accountDTO.toEntity());			
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;		
	}
	
	/** 
	 * 직원 정보 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> payload) {
		int result = 0;
		// 직원 정보 삭제 (DELETE)
		try {
			for (int m=0; m<payload.size(); m++) {
				accountRepository.deleteById(payload.get(m));
				result++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	/**
	 * 비밀번호 초기화
	 * 참조: https://kitty-geno.tistory.com/43
	 * @param accountDTO
	 * @return
	 */
	public Integer resetPassword(AccountDTO accountDTO) {
		// 기본 변수 선언
		int result = 500;
		String email = accountDTO.getEmail();
		// 임시 비밀번호 생성
		String tempPassword = getTempPassword();
		// account 객체 선언
		Account account = null;
		
		// 임시 비밀번호 발송 내용 설정 및 메일 발송 처리
		try {
			// 존재하는 이메일인지 확인
			account = accountRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 직원 정보가 없습니다. id: "+email));
			// 이메일로 임시 비밀번호를 발송할 내용 설정
			MailDTO mailDTO = setMail(email, tempPassword);
			// 임시 비밀번호 메일 발송
			sendMail(mailDTO);
			log.info(email+"로 임시 비밀번호를 발송했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("임시 비밀번호 발송 실패");
			
			return result;
		}
		
		// 해당 사용자의 비밀번호를 임시 비밀번호로 변경
		try {
			// 이메일 정보를 담은 Entity 값을 DTO에 set
			accountDTO.setId(account.getId());
			accountDTO.setPassword(encoder.encode(tempPassword));
			accountDTO.setFailCount(0);
			accountDTO.setStatus(AccountStatus.ACTIVE);
			// 비밀번호 초기화 (DTO -> Entity)
			accountRepository.save(accountDTO.toEntity());
			result = 200;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 8자리 난수로 임시 비밀번호 생성
	 * @return
	 */
	public String getTempPassword() {
		char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8',
				 					 '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				 					 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				 					 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String tempPassword = "";	// 임시 비밀번호 값
		int index = 0;	// 임시 비밀번호로 생성할 난수 문자열의 index 값
		int length = 8;	// 임시 비밀번호 길이
		
		for (int i=0; i<length; i++) {
			index = (int) (charSet.length * Math.random());
			tempPassword += charSet[index];
		}
		
		log.info("**** 생성된 임시 비밀번호: {}", tempPassword);
		
		return tempPassword;
	}
	
	/**
	 * 이메일로 임시 비밀번호를 발송할 내용 설정
	 */
	public MailDTO setMail(String email, String tempPassword) {
		String title = "[OMS 발송] "+email+" 계정의 임시 비밀번호 안내 메일입니다.";
		String message = """
						 안녕하세요. %email 계정의 임시 비밀번호를 아래와 같이 보내드립니다.
						 임시 비밀번호로 접속하시면 비밀번호 초기화로 안내드리겠습니다.
						 
						 =============================
						  ◆ 계정의 임시 비밀번호: %tempPassword
						 =============================
						 
						 감사합니다!
						 """.replace("%tempPassword", tempPassword)
							.replace("%email", email);
		
		MailDTO mailDTO = new MailDTO();
		mailDTO.setAddress(email);
		mailDTO.setTitle(title);
		mailDTO.setMessage(message);
		
		return mailDTO;
	}
	
	/**
	 * 임시 비밀번호를 이메일로 전송
	 * @param mailDTO
	 */
	public void sendMail(MailDTO mailDTO) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(mailDTO.getAddress());
		simpleMailMessage.setFrom(FROM_ADDRESS);
		simpleMailMessage.setSubject(mailDTO.getTitle());
		simpleMailMessage.setText(mailDTO.getMessage());
		
		log.info("************** result: {}", simpleMailMessage);
		
		javaMailSender.send(simpleMailMessage);
	}
}
