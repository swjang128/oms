package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.config.ResponseCode;
import com.oms.dto.AccountDTO;
import com.oms.dto.MailDTO;
import com.oms.entity.Account;
import com.oms.entity.Account.Role;
import com.oms.entity.Account.Status;
import com.oms.entity.Account.UserStatus;
import com.oms.repository.AccountRepository;
import com.oms.specification.AccountSpecification;

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
	 * 특정 계정정보 조회
	 * 
	 * @param accountDTO
	 * @param account
	 * @return
	 */
	public Account readAccount(AccountDTO accountDTO, Account account) {
		account = accountRepository.findByEmail(accountDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("계정이 존재하지 않습니다. email: " + accountDTO.getEmail()));
		return account;
	}
	
	/**
	 * 계정 등록
	 * 
	 * @param accountDTO
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> create(AccountDTO accountDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// Status는 Active로 Set
		accountDTO.setStatus(Account.Status.ACTIVE);
		// 입력받아온 비밀번호는 암호화
		accountDTO.setPassword(encoder.encode(accountDTO.getPassword()));
		// UserStatus는 OFFLINE으로 Set
		accountDTO.setUserStatus(Account.UserStatus.OFFLINE);
		// accountDTO를 Entity로 변환
		Account account = accountDTO.toEntity();
		// 회원 가입 진행
		try {
			accountRepository.save(account);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		} finally {
			resultMap.put("status", status);
			resultMap.put("message", message);
		}
		return resultMap;
	}

	/**
	 * 계정 목록 조회 (READ)
	 * 
	 * @return List<Account>
	 */
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		// 기본 변수 설정
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Account> account = new ArrayList<Account>();;
		List<AccountDTO> accountDTO = new ArrayList<AccountDTO>();
		Object accountStatus = paramMap.get("status");
		Object userStatus = paramMap.get("userStatus");
		Object role = paramMap.get("role");
		Object department = paramMap.get("department");
		Object position = paramMap.get("position");
		Specification<Account> specification = (root, query, criteriaBuilder) -> null;
		// 계정 목록 조회
		try {
			if (accountStatus != null)
				specification = specification.and(AccountSpecification.findByStatus(accountStatus));
			if (userStatus != null)
				specification = specification.and(AccountSpecification.findByUserStatus(userStatus));
			if (role != null)
				specification = specification.and(AccountSpecification.findByRole(role));
			if (department != null)
				specification = specification.and(AccountSpecification.findByDepartment(department));
			if (position != null)
				specification = specification.and(AccountSpecification.findByPosition(position));
			account = accountRepository.findAll(specification);
			accountDTO = account.stream().map(a -> modelMapper.map(a, AccountDTO.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// resultMap에 담기
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("accountList", accountDTO);		
		return resultMap;
	}
	
	/**
	 * 특정 계정 조회 (READ)
	 * @param Long
	 * @return Map<String, Object>
	 */
	public Map<String, Object> readOne(Long id, Map<String, Object> resultMap) {
		// 기본 변수 설정
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		Account account = null;
		AccountDTO accountDTO = null;
		// 특정 계정 조회
		try {			
			account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 계정 정보가 없습니다. id: " + id));
			accountDTO = new AccountDTO(account);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ACCOUNT_NOT_FOUND;
			message = ResponseCode.Message.ACCOUNT_NOT_FOUND;
		}
		// resultMap에 담기
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("account", accountDTO);		
		return resultMap;
	}

	/**
	 * 계정 정보 수정 (UPDATE)
	 * 
	 * @param @RequestBody
	 * @return
	 * @return
	 */
	@Transactional
	public Map<String, Object> update(AccountDTO accountDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		long id = accountDTO.getId();
		Account account = null;
		log.info("****** 비밀번호: {}", accountDTO.getPassword());
		// 해당 계정이 있는지 확인
		try {
			account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 계정 정보가 없습니다. id: " + id));
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ACCOUNT_NOT_FOUND;
			message = ResponseCode.Message.ACCOUNT_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}
		
		// 비밀번호 확인
		if (!encoder.matches(accountDTO.getPassword(), account.getPassword())) {
			status = ResponseCode.Status.ACCOUNT_PASSWORD_NOT_MATCH;
			message = ResponseCode.Message.ACCOUNT_PASSWORD_NOT_MATCH;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}
			
		// 계정 정보 수정 (UPDATE)
		try {
			account = accountDTO.toEntity();
			log.info("****** accountDTO.toEntity(): {}", account.getName());
			accountRepository.save(account);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		} finally {
			resultMap.put("status", status);
			resultMap.put("message", message);
		}
		return resultMap;
	}

	/**
	 * 계정 정보 삭제 (DELETE)
	 * 
	 * @return http 응답코드
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> payload, Map<String, Object> resultMap) {
		String[] deleteAccount = new String[payload.size()];
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		Optional<Account> targetAccount;

		// 대상 계정이 존재하는지 확인
		try {
			for (int t = 0; t < payload.size(); t++) {
				log.info("****** 페이로드: {}", payload.get(t));
				targetAccount = accountRepository.findById(payload.get(t));
				log.info("****** 대상 계정: {}", targetAccount);
				deleteAccount[t] = targetAccount.get().getEmail();
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ACCOUNT_NOT_FOUND;
			message = ResponseCode.Message.ACCOUNT_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}

		// 모두 존재하는 것을 확인하면 계정 삭제
		try {
			for (int d = 0; d < payload.size(); d++) {
				accountRepository.deleteById(payload.get(d));
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		} finally {
			resultMap.put("status", status);
			resultMap.put("message", message);
			resultMap.put("deleteAccount", deleteAccount);
		}
		return resultMap;
	}

	/**
	 * 비밀번호 초기화
	 * 
	 * @param accountDTO
	 * @return
	 */
	public Map<String, Object> resetPassword(AccountDTO accountDTO, Map<String, Object> resultMap) {
		// 기본 변수 선언
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		String email = accountDTO.getEmail();
		// 임시 비밀번호 생성
		String tempPassword = getTempPassword();
		// account 객체 선언
		Account account = null;

		// 계정이 존재하는지 확인
		try {
			account = readAccount(accountDTO, account);
		} catch (Exception e) { // 계정이 존재하지 않는 경우
			e.printStackTrace();
			status = ResponseCode.Status.ACCOUNT_NOT_FOUND;
			message = ResponseCode.Message.ACCOUNT_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}

		// 임시 비밀번호 메일 발송
		try {
			// 이메일로 임시 비밀번호를 발송할 내용 설정
			MailDTO mailDTO = setMail(email, tempPassword);
			// 임시 비밀번호 메일 발송
			sendMail(mailDTO);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}

		// 해당 사용자의 비밀번호를 임시 비밀번호로 변경
		try {
			accountRepository.updatePassword(account.getId(), encoder.encode(tempPassword), 0, Account.Status.EXPIRED.getKey(), Account.UserStatus.OFFLINE.getKey());	
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}

		// resultMap 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * 비밀번호 변경
	 * 
	 * @param accountDTO
	 * @return status
	 */
	public Map<String, Object> updatePassword(AccountDTO accountDTO, Map<String, Object> resultMap) {
		// 기본 변수 선언
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		Account account = null;

		// 계정의 유무 확인
		try {
			account = readAccount(accountDTO, account);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ACCOUNT_NOT_FOUND;
			message = ResponseCode.Message.ACCOUNT_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}

		// 계정의 상태가 BLOCKED인 경우 비밀번호 변경이 불가능
		if (account.getStatus() == Account.Status.BLOCKED) {
			status = ResponseCode.Status.ACCOUNT_BLOCKED;
			message = ResponseCode.Message.ACCOUNT_BLOCKED;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}

		// 비밀번호 변경
		if (encoder.matches(accountDTO.getOldPassword(), account.getPassword())) {
			// 해당 사용자의 비밀번호를 신규 비밀번호로 변경
			try {
				accountRepository.updatePassword(account.getId(), encoder.encode(accountDTO.getPassword()), 0, Account.Status.ACTIVE.getKey(), Account.UserStatus.OFFLINE.getKey());	
			} catch (Exception e) {
				e.printStackTrace();
				status = ResponseCode.Status.ERROR_ABORT;
				message = ResponseCode.Message.ERROR_ABORT;
			}
		} else {
			status = ResponseCode.Status.ACCOUNT_OLD_PASSWORD_NOT_MATCH;
			message = ResponseCode.Message.ACCOUNT_OLD_PASSWORD_NOT_MATCH;
		}
		
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}

	/**
	 * 8자리 난수로 임시 비밀번호 생성
	 * 
	 * @return
	 */
	public String getTempPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		String tempPassword = ""; // 임시 비밀번호 값
		int index = 0; // 임시 비밀번호로 생성할 난수 문자열의 index 값
		int length = 8; // 임시 비밀번호 길이

		for (int i = 0; i < length; i++) {
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
		String title = "[OMS 발송] " + email + " 계정의 임시 비밀번호 안내 메일입니다.";
		String message = """
				안녕하세요. %email 계정의 임시 비밀번호를 아래와 같이 보내드립니다.
				임시 비밀번호로 접속하시면 비밀번호 초기화로 안내드리겠습니다.

				==================================
				◆ 계정의 임시 비밀번호: %tempPassword
				==================================

				감사합니다!
				""".replace("%tempPassword", tempPassword).replace("%email", email);

		MailDTO mailDTO = new MailDTO();
		mailDTO.setAddress(email);
		mailDTO.setTitle(title);
		mailDTO.setMessage(message);

		return mailDTO;
	}

	/**
	 * 임시 비밀번호를 이메일로 전송
	 * 
	 * @param mailDTO
	 */
	public void sendMail(MailDTO mailDTO) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(mailDTO.getAddress());
		simpleMailMessage.setFrom(FROM_ADDRESS);
		simpleMailMessage.setSubject(mailDTO.getTitle());
		simpleMailMessage.setText(mailDTO.getMessage());

		javaMailSender.send(simpleMailMessage);
	}

	/**
	 * 이메일 중복 체크
	 * 
	 * @param email
	 * @return Map<String, Object>
	 */
	public Map<String, Object> checkEmail(String email, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.ACCOUNT_EXIST;
		String message = ResponseCode.Message.ACCOUNT_EXIST;
		try {
			Optional<Account> account = accountRepository.findByEmail(email);
			if (!account.isPresent()) {
				status = ResponseCode.Status.ACCOUNT_NOT_FOUND;
				message = ResponseCode.Message.ACCOUNT_NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		} finally {
			resultMap.put("status", status);
			resultMap.put("message", message);
		}
		return resultMap;
	}
	
	/**
	 * 특정 상태(Status)인 계정 조회 (READ)
	 * 
	 * @return List<Account>
	 */
	public Map<String, Object> findByStatus(List<Status> accountStatus, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Account> account = new ArrayList<Account>();;
		List<AccountDTO> accountDTO = new ArrayList<AccountDTO>();		
		// 특정 상태(Status)인 계정
		try {
			for (int s=0; s<accountStatus.size(); s++) {
				account = accountRepository.findByStatus(accountStatus.get(s));
				accountDTO.addAll(account.stream().map(a -> modelMapper.map(a, AccountDTO.class)).collect(Collectors.toList())) ;
			}
			resultMap.put("accountList", accountDTO);
			resultMap.put("accountSize", accountDTO.size());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// resultMap에 담기
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * 특정 UserStatus인 계정 조회 (READ)
	 * 
	 * @return List<Account>
	 */
	public Map<String, Object> findByUserStatus(List<UserStatus> userStatus, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Account> account = new ArrayList<Account>();;
		List<AccountDTO> accountDTO = new ArrayList<AccountDTO>();
		// 특정 계정 목록 조회
		try {
			for (int u=0; u<userStatus.size(); u++) {
				account = accountRepository.findByUserStatus(userStatus.get(u));
				accountDTO.addAll(account.stream().map(a -> modelMapper.map(a, AccountDTO.class)).collect(Collectors.toList()));	
			}
			resultMap.put("accountList", accountDTO);
			resultMap.put("accountSize", accountDTO.size());
		} catch (Exception e) {
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// resultMap에 담기
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * 사용자의 userStatus 변경
	 * (UPDATE)
	 * @return List<Account>
	 */
	public Map<String, Object> updateUserStatus(AccountDTO accountDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		
		// 사용자의 상태 변경
		try {
			accountRepository.updateUserStatus(accountDTO.getEmail(), accountDTO.getUserStatus().getKey());
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
}
