package com.capias.admin.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.capias.security.config.AccountRole;
import com.capias.security.config.AccountStatus;
import com.capias.admin.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {	
	private Long id;										// 사번
	
	@NotBlank(message="부서를 선택해주세요.")
	@Size(min=2, max=16, message="부서 값이 잘못되었습니다.")
	private String department;								// 부서
	
	@NotBlank(message="직책을 선택해주세요.")
	@Size(min=2, max=16, message="직책 값이 잘못되었습니다.")
	private String position;								// 직책
	
	private String photo;									// 사진
	
	@NotBlank(message="이름을 입력해주세요.")
	@Size(min=1, max=64, message="이름은 64자 이하로 입력해주세요.")
	private String name;				 					// 이름
	
	@NotBlank(message="이메일을 입력해주세요.")
	@Size(min=1, max=64, message="이메일은 64자 이하로 입력해주세요.")
	@Email(message="이메일 형식에 맞지 않습니다.")
    private String email;									// 이메일
    
	@NotBlank(message="비밀번호를 입력해주세요.")
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
    		 message="비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;								// 비밀번호
	
	private int failCount;									// 비밀번호 틀린 횟수
	
	private AccountStatus status;									// 상태
	
	private AccountRole role;										// 권한
	
	@NotBlank(message="연락처를 입력해주세요.")
	@Pattern(regexp="(01[016789])(\\d{3,4})(\\d{4})",
			 message="올바른 연락처를 입력해주세요.")
	@Size(min=1, max=16, message="올바른 연락처를 입력해주세요")
    private String phone;									// 연락처
	
	@Pattern(regexp="(01[016789])(\\d{3,4})(\\d{4})",
			 message="올바른 비상 연락처를 입력해주세요.")
	@Size(min=1, max=16, message="올바른 비상 연락처를 입력해주세요")
	private String emergencyContact;						// 비상연락처
	
	private Date birthday;									// 생일
	
	private Date hireDate;									// 입사일

	/**
	 * (Request) DTO -> Entity
	 * MemberDTO를 Entity로 변환 
	 * @return
	 */
	public Member toEntity() {
		return Member.builder()
				.id(id)
				.department(department)
				.position(position)
				.photo(photo)
				.name(name)
				.email(email)
				.password(password)
				.status(status)
				.role(role)
				.phone(phone)
				.emergencyContact(emergencyContact)
				.birthday(birthday)
				.hireDate(hireDate)
				.build();
	}
	
	/**
	 * (Response) Entity -> DTO
	 * Member Entity 정보를 DTO로 받아 응답받는 메소드
	 * @param member
	 */
	public MemberDTO (Member member) {
		this.id = member.getId();
		this.department = member.getDepartment();
		this.position = member.getPosition();
		this.photo = member.getPhoto();
		this.name = member.getName();
		this.email = member.getEmail();
		this.password = member.getPassword();
		this.failCount = member.getFailCount();
		this.status = member.getStatus();
		this.role = member.getRole();
		this.phone = member.getPhone();
		this.emergencyContact = member.getEmergencyContact();
		this.birthday = member.getBirthday();
		this.hireDate = member.getHireDate();
	}
	
}
