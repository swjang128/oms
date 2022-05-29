/******************************************************************** Ready *****************************************************************************/
$(function() {
	/**************************
	* 로그인 입력폼 변경 감지 *
	**************************/
	$('#email').change(function() {
		let email = $('#email').val();
		var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
		// 이메일이 비어있을 때
		if (!email) {
			$('#loginResultFeedback').text('이메일을 입력해주세요');
			$('#email').focus();
			return;
		}
		// 이메일 정규식 위반시
		if (validateEmail.test(email) == false) {
			$('#loginResultFeedback').text('올바른 이메일 양식으로 입력해주세요');
			$('#email').focus();
			return;
		}
		// 이메일 정규식을 통과하면 피드백 메시지 삭제
		$('#loginResultFeedback').text('');
	});
	
	$('#password').change(function() {
		let password = $('#password').val();
		// 비밀번호가 비어있을 때
		if (!password) {
			$('#loginResultFeedback').text('비밀번호를 입력해주세요');
			$('#password').focus();
			return;
		}
		// 비밀번호 정규식을 통과하면 피드백 메시지 삭제
		$('#loginResultFeedback').text('');
	});
});

/******************************************************************** 계정 등록 관련 *****************************************************************************/
/*****************
* 부서, 직급 조회 *
******************/
function initAccountModal() {
	// 부서 옵션이 존재하지 않을 때만 ajax 호출
	if ($('#createDepartment option').length < 2) {
		// 부서 조회 후 셀렉스박스 옵션에 append
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department?use=Y',
			type: 'GET',
			cache: false,
			datatype: 'json',
			success: function(result) {
				if (result.departmentList.length > 0) {
					for (var d = 0; d < result.departmentList.length; d++) {
						$('#createDepartment').append('<option value="' + result.departmentList[d].name + '">' + result.departmentList[d].name + '</option>');
					}
					return;
				}
				$('#labelDepartment').removeClass('text-success');
				$('#labelDepartment').addClass('text-danger');
				$('#labelDepartment').html('<i class="bi-exclamation-triangle me-1"></i> 부서가 존재하지 않습니다');
			},
			error: function() {
				$('#labelDepartment').removeClass('text-success');
				$('#labelDepartment').addClass('text-danger');
				$('#labelDepartment').html('<i class="bi-exclamation-triangle me-1"></i> 부서 목록 동기화 실패');
			}
		});
	}

	// 직급 옵션이 존재하지 않을 때만 ajax 호출
	if ($('#createPosition option').length < 2) {
		// 직급 조회 후 셀렉스박스 옵션에 append
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/position?use=Y',
			type: 'GET',
			cache: false,
			datatype: 'json',
			success: function(result) {
				for (var d = 0; d < result.positionList.length; d++) {
					$('#createPosition').append('<option value="' + result.positionList[d].name + '">' + result.positionList[d].name + '</option>');
				}
			},
			error: function() {
				$('#labelPosition').removeClass('text-success');
				$('#labelPosition').addClass('text-danger');
				$('#labelPosition').html('<i class="bi-exclamation-triangle me-1"></i> 직급 목록 동기화 실패');
			}
		});
	}
}

/****************************
* 연락처 입력시 자동 하이픈 *
 ****************************/
$(document).on('keyup', '#createPhone, #createEmergencyContact', function() {
	$(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3").replace("--", "-"));
});

/************
* 이름 검증 *
*************/
function nameCheck() {
	// name 파라미터 검증
	var validateName = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|]+$/;
	var name = $('#createName').val();
	// name 값이 비어있을 때
	if (!name) {
		$('#labelName').removeClass('text-success');
		$('#labelName').addClass('text-danger');
		$('#labelName').html('<i class="bi-exclamation-triangle me-1"></i> 이름을 입력하세요');
		$('#createName').focus();
		return;
	}
	// name validation이 false 일 때
	if (validateName.test(name) == false) {
		$('#labelName').removeClass('text-success');
		$('#labelName').addClass('text-danger');
		$('#labelName').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이름 형식');
		$('#createName').focus();
		return;
	} else {	// 올바른 name validation 일 때
		$('#labelName').removeClass('text-danger');
		$('#labelName').addClass('text-success');
		$('#labelName').html('<i class="bi-check-lg me-1"></i> 이름');
		return name;
	}
}

/**************
* 이메일 검증 *
***************/
function emailCheck() {
	// email 파라미터 검증
	var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var email = $('#createEmail').val();
	// email 값이 비어있을 때
	if (!email) {
		$('#labelEmail').removeClass('text-success');
		$('#labelEmail').addClass('text-danger');
		$('#labelEmail').html('<i class="bi-exclamation-triangle me-1"></i> 이메일을 입력하세요');
		$('#create-email').focus();
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#labelEmail').removeClass('text-success');
		$('#labelEmail').addClass('text-danger');
		$('#labelEmail').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		$('#createEmail').focus();
		return;
	}
	// 이메일 중복 확인
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/checkEmail/' + email,
		type: 'GET',
		cache: false,
		success: function(result) {
			$('#modalMessage').text(result.message);
			if (result.status == 1004) {	// 생성 가능한 이메일인 경우
				$('#labelEmail').removeClass('text-danger');
				$('#labelEmail').addClass('text-success');
				$('#labelEmail').html('<i class="bi-check-lg me-1"></i> 이메일');
				return email;
			} else if (result.status == 1009) {	// 중복되는 이메일인 경우
				$('#labelEmail').removeClass('text-success');
				$('#labelEmail').addClass('text-danger');
				$('#labelEmail').html('<i class="bi-exclamation-triangle me-1"></i> 중복된 이메일');
				$('#createEmail').focus();
				return;
			} else {
				$('#labelEmail').removeClass('text-success');
				$('#labelEmail').addClass('text-danger');
				$('#labelEmail').html('<i class="bi-exclamation-triangle me-1"></i> 이메일 (내부 서버 오류가 발생하였습니다');
				$('#createEmail').focus();
				return;
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
			return;
		}
	});

	if (email) {
		return email;
	}
}

/**************
* 연락처 검증 *
***************/
function phoneCheck() {
	// phone 파라미터 검증
	var validatePhone = /^0([0-9]{1,2})-?([0-9]{3,4})-?([0-9]{3,4})$/;
	var phone = $('#createPhone').val();
	// phone 값이 비어있을 때
	if (!phone) {
		$('#labelPhone').removeClass('text-success');
		$('#labelPhone').addClass('text-danger');
		$('#labelPhone').html('<i class="bi-exclamation-triangle me-1"></i> 연락처를 입력하세요');
		$('#createPhone').focus();
		return;
	}
	// 연락처 정규식 검증
	if (validatePhone.test(phone) == false) {	// 연락처 정규식 위반
		$('#labelPhone').removeClass('text-success');
		$('#labelPhone').addClass('text-danger');
		$('#labelPhone').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 연락처 형식');
		$('#createPhone').focus();
		return;
	} else {	// 올바른 연락처인 경우 phone 값을 return
		$('#labelPhone').removeClass('text-danger');
		$('#labelPhone').addClass('text-success');
		$('#labelPhone').html('<i class="bi-check-lg me-1"></i> 연락처');
		phone = phone.replaceAll('-', '');
		return phone;
	}
}

/******************
* 비상연락처 검증 *
*******************/
function emergencyContactCheck() {
	// 비상연락처 파라미터 검증
	var validateEmergencyContact = /^0([0-9]{1,2})-?([0-9]{3,4})-?([0-9]{3,4})$/;
	var emergencyContact = $('#createEmergencyContact').val();
	// 비상연락처 값이 비어있을 때
	if (!emergencyContact) {
		$('#labelEmergencyContact').removeClass('text-success');
		$('#labelEmergencyContact').removeClass('text-danger');
		$('#labelEmergencyContact').text('비상연락처');
		return;
	}
	// 비상연락처 정규식 검증
	if (validateEmergencyContact.test(emergencyContact) == false) {		// 비상연락처 정규식 위반
		$('#labelEmergencyContact').removeClass('text-success');
		$('#labelEmergencyContact').addClass('text-danger');
		$('#labelEmergencyContact').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 비상연락처 형식');
		$('#createEmergencyContact').focus();
		return;
	} else {	// 올바른 비상연락처인 경우 emergencyContact 값을 return
		$('#labelEmergencyContact').removeClass('text-danger');
		$('#labelEmergencyContact').addClass('text-success');
		$('#labelEmergencyContact').html('<i class="bi-check-lg me-1"></i> 비상연락처');
		emergencyContact = emergencyContact.replaceAll('-', '');
		return emergencyContact;
	}
}

/************
* 직급 검증 *
*************/
function positionCheck() {
	var position = $('#createPosition').val();
	// position 값이 비어있을 때
	if (!position) {
		$('#labelPosition').removeClass('text-success');
		$('#labelPosition').addClass('text-danger');
		$('#labelPosition').html('<i class="bi-exclamation-triangle me-1"></i> 직급을 선택하세요');
		$('#create-position').focus();
		return;
	} else {	// position을 선택했을 때
		$('#labelPosition').removeClass('text-danger');
		$('#labelPosition').addClass('text-success');
		$('#labelPosition').html('<i class="bi-check-lg me-1"></i> 직급');
		return position;
	}
}

/************
*  부서 검증 *
*************/
function departmentCheck() {
	var department = $('#createDepartment').val();
	// department 값이 비어있을 때
	if (!department) {
		$('#labelDepartment').removeClass('text-success');
		$('#labelDepartment').addClass('text-danger');
		$('#labelDepartment').html('<i class="bi-exclamation-triangle me-1"></i> 부서를 선택하세요');
		$('#createDepartment').focus();
		return;
	} else {	// department을 선택했을 때
		$('#labelDepartment').removeClass('text-danger');
		$('#labelDepartment').addClass('text-success');
		$('#labelDepartment').html('<i class="bi-check-lg me-1"></i> 부서');
		return department;
	}
}

/****************
*  주소 검색 API *
*****************/
function searchAddress() {
	new daum.Postcode({
		oncomplete: function(data) {
			// address에 받아온 주소 넣기
			$('#createAddress').val(data.address);
			$('#labelAddress').removeClass('text-danger');
			$('#labelAddress').addClass('text-success');
			$('#labelAddress').html('<i class="bi-check-lg me-1"></i> 주소');
			// 상세입력 focus
			document.querySelector('input[name=createAddressDetail]').focus();
		}
	}).open();
}

/************
* 주소 검증 *
*************/
function addressCheck() {
	var address = $('#createAddress').val();
	// address 값이 비어있을 때
	if (!address) {
		$('#labelAddress').removeClass('text-success');
		$('#labelAddress').addClass('text-danger');
		$('#labelAddress').html('<i class="bi-exclamation-triangle me-1"></i> 주소를 입력하세요');
		$('#createAddress').focus();
		return;
	} else {
		$('#labelAddress').removeClass('text-danger');
		$('#labelAddress').addClass('text-success');
		$('#labelAddress').html('<i class="bi-check-lg me-1"></i> 주소');
		return address;
	}
}

/****************
*  상세주소 검증 *
*****************/
function addressDetailCheck() {
	var addressDetail = $('#createAddressDetail').val();
	// addressDetail 값이 비어있을 때
	if (!addressDetail) {
		$('#labelAddressDetail').removeClass('text-success');
		$('#labelAddressDetail').removeClass('text-danger');
		$('#labelAddressDetail').TEXT('상세주소');
	} else {	// addressDetail 값을 입력했을 때
		$('#labelAddressDetail').removeClass('text-danger');
		$('#labelAddressDetail').addClass('text-success');
		$('#labelAddressDetail').html('<i class="bi-check-lg me-1"></i> 상세주소');
	}
	return addressDetail;
}

/************
*  권한 검증 *
*************/
function roleCheck() {
	var role = $(' input[name="createRole"]:checked ').val();
	// role을 선택하지 않았을 때
	if (!role) {
		$('#labelRole').removeClass('text-success');
		$('#labelRole').addClass('text-danger');
		$('#labelRole').html('<i class="bi-exclamation-triangle me-1"></i> 권한을 선택해주세요');
		$('#createRole').focus();
		return;
	} else {	// role을 선택했을 때
		$('#labelRole').removeClass('text-danger');
		$('#labelRole').addClass('text-success');
		$('#labelRole').html('<i class="bi-check-lg me-1"></i> 권한');
		return role;
	}
}

/****************
* 비밀번호 검증 *
*****************/
function passwordCheck() {
	// password 파라미터 검증
	var validatePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	var password = $('#createPassword').val();
	// password 값이 비어있을 때
	if (!password) {
		$('#labelPassword').removeClass('text-success');
		$('#labelPassword').addClass('text-danger');
		$('#labelPassword').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호를 입력하세요');
		$('#createPassword').focus();
		return;
	}
	// password validate가 false 일 때
	if (validatePassword.test(password) == false) {
		$('#labelPassword').removeClass('text-success');
		$('#labelPassword').addClass('text-danger');
		$('#labelPassword').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호는 8자 이상의 대,소문자, 특수문자, 숫자를 포함해야합니다');
		$('#createPassword').focus();
		return;
	} else {
		$('#labelPassword').removeClass('text-danger');
		$('#labelPassword').addClass('text-success');
		$('#labelPassword').html('<i class="bi-check-lg me-1"></i> 비밀번호');
		// password와 passwordConfirm의 값이 다를 때
		var passwordConfirm = $('#createPasswordConfirm').val();
		if (password != passwordConfirm) {
			$('#labelPasswordConfirm').removeClass('text-success');
			$('#labelPasswordConfirm').addClass('text-danger');
			$('#labelPasswordConfirm').html('<i class="bi-exclamation-triangle me-1"></i> 동일한 비밀번호를 입력하세요');
			$('#createPasswordConfirm').focus();
			return;
		} else {
			$('#labelPasswordConfirm').removeClass('text-danger');
			$('#labelPasswordConfirm').addClass('text-success');
			$('#labelPasswordConfirm').html('<i class="bi-check-lg me-1"></i> 비밀번호 확인');
			return password;
		}
	}
}

/*********************
* 비밀번호 확인 검증 *
**********************/
function passwordConfirmCheck() {
	var password = $('#createPassword').val();
	var passwordConfirm = $('#createPasswordConfirm').val();
	// passwordConfirm 값이 비어있을 때
	if (!passwordConfirm) {
		$('#labelPasswordConfirm').removeClass('text-success');
		$('#labelPasswordConfirm').addClass('text-danger');
		$('#labelPasswordConfirm').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호 확인을 입력하세요');
		$('#createPasswordConfirm').focus();
		return;
	}
	// password와 passwordConfirm의 값이 다를 때
	if (password != passwordConfirm) {
		$('#labelPasswordConfirm').removeClass('text-success');
		$('#labelPasswordConfirm').addClass('text-danger');
		$('#labelPasswordConfirm').html('<i class="bi-exclamation-triangle me-1"></i> 동일한 비밀번호를 입력하세요');
		$('#createPasswordConfirm').focus();
		return;
	} else {
		$('#labelPasswordConfirm').removeClass('text-danger');
		$('#labelPasswordConfirm').addClass('text-success');
		$('#labelPasswordConfirm').html('<i class="bi-check-lg me-1"></i> 비밀번호 확인');
		return passwordConfirm;
	}
}

/**************************
*  기본 비밀번호 사용 여부 *
***************************/
function defaultPasswordCheck() {
	var defaultPassword = $('#createDefaultPassword').is(':checked');
	// 기본 비밀번호를 사용하는 경우
	if (defaultPassword == true) {
		$('#divDefaultPassword').removeClass('d-none');
		$('#divPassword').addClass('d-none');
		$('#divPasswordConfirm').addClass('d-none');
		$('#spanDefaultPasswordComma').removeClass('d-none');
		$('#spanDefaultPassword').removeClass('d-none');
		$('#createPassword').val('');
		$('#createPasswordConfirm').val('');
		return defaultPassword;
	} else {	// 기본 비밀번호 체크해제
		$('#divDefaultPassword').addClass('d-none');
		$('#divPassword').removeClass('d-none');
		$('#divPasswordConfirm').removeClass('d-none');
		$('#spanDefaultPasswordComma').addClass('d-none');
		$('#spanDefaultPassword').addClass('d-none');
		$('#createPassword').val('');
		$('#createPasswordConfirm').val('');
		return;
	}
}

/****************
*  생년월일 검증 *
*****************/
function birthdayCheck() {
	var birthday = $('#createBirthday').val();
	// birthday 값이 비어있을 때
	if (!birthday) {
		$('#labelBirthday').removeClass('text-success');
		$('#labelBirthday').removeClass('text-danger');
		$('#labelBirthday').text('생년월일');
	} else {	// birthday 값을 입력했을 때
		$('#labelBirthday').removeClass('text-danger');
		$('#labelBirthday').addClass('text-success');
		$('#labelBirthday').html('<i class="bi-check-lg me-1"></i> 생년월일');
	}
	return birthday;
}

/**************
*  입사일 검증 *
***************/
function hireDateCheck() {
	var hireDate = $('#createHireDate').val();
	// hireDate 값이 비어있을 때
	if (!hireDate) {
		$('#labelHireDate').removeClass('text-success');
		$('#labelHireDate').removeClass('text-danger');
		$('#labelHireDate').text('입사일');
	} else {	// addressDetail 값을 입력했을 때
		$('#labelHireDate').removeClass('text-danger');
		$('#labelHireDate').addClass('text-success');
		$('#labelHireDate').html('<i class="bi-check-lg me-1"></i> 입사일');
	}
	return hireDate;
}

/************
*  직원 등록 *
*************/
function createAccount() {
	// 이름 검증 (필수)
	var name = nameCheck();
	if (!name) {
		return;
	}
	// 이메일 검증 (필수)
	var email = emailCheck();
	if (!email) {
		return;
	}
	// 연락처 검증 (필수)
	var phone = phoneCheck();
	if (!phone) {
		return;
	}
	// 비상연락처 검증 (선택)
	var emergencyContact = emergencyContactCheck();
	// 직급 검증 (필수)
	var position = positionCheck();
	if (!position) {
		return;
	}
	// 부서 검증 (필수)
	var department = departmentCheck();
	if (!department) {
		return;
	}
	// 주소 검증 (필수)
	var address = addressCheck();
	if (!address) {
		return;
	}
	// 상세주소 검증 (선택)
	var addressDetail = addressDetailCheck();
	// 권한 검증 (필수)	
	var role = roleCheck();
	if (!role) {
		return;
	}
	// 비밀번호 검증 (필수)
	var defaultPassword = $('#createDefaultPassword').is(':checked');
	if (defaultPassword == true) { // 기본 비밀번호를 사용하는 경우
		var password = $('#spanDefaultPassword').val();
	} else { // 일반 비밀번호를 사용하는 경우
		var password = passwordCheck();
		if (!password) {
			return;
		}
	}
	// 생일 검증 (선택)
	var birthday = birthdayCheck();
	// 입사일 검증 (선택)
	var hireDate = hireDateCheck();

	// 입력받은 모든 정보를 createData(payload)에 추가
	var createData = JSON.stringify({
		hireDate: hireDate,
		birthday: birthday,
		password: password,
		role: role,
		addressDetail: addressDetail,
		address: address,
		position: position,
		department: department,
		emergencyContact: emergencyContact,
		phone: phone,
		email: email,
		name: name
	});

	// 직원 등록 처리
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account',
		type: 'POST',
		data: createData,
		cache: false,
		success: function(result) {
			if (result.status == 201) {
				alert('계정을 정상적으로 등록하였습니다');
				location.reload();
			} else {
				alert('내부 서버 오류가 발생하였습니다');
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		}
	});
}

/************************
* rememberMe Check *
*************************/
function rememberMeCheck() {
	var rememberMe = $('#rememberMe').is(':checked');
	if (rememberMe == false) {
		$('#rememberMeMessage').removeClass('text-dark');
		$('#rememberMeMessage').addClass('text-muted');
	} else {
		$('#rememberMeMessage').removeClass('text-muted');
		$('#rememberMeMessage').addClass('text-dark');
	}
}


/**************************************************************** 비밀번호 초기화 관련 ************************************************************************************/
/*********************
* 엔터키 입력 이벤트 *
**********************/
function enterResetPassword() {
	if (window.event.keyCode == 13) {
		resetPassword();
	}
}

/**************
* 이메일 검증 *
***************/
function resetPasswordEmailCheck() {
	// email 파라미터 검증
	var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var resetPasswordEmail = $('#resetPasswordEmail').val();
	// email 값이 비어있을 때
	if (!resetPasswordEmail) {
		$('#labelResetPasswordEmail').removeClass('text-success');
		$('#labelResetPasswordEmail').addClass('text-danger');
		$('#labelResetPasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 이메일을 입력하세요');
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(resetPasswordEmail) == false) {
		$('#labelResetPasswordEmail').removeClass('text-success');
		$('#labelResetPasswordEmail').addClass('text-danger');
		$('#labelResetPasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식입니다');
		return;
	}
	// 정상적인 email을 입력했을 때, 비밀번호 초기화 버튼 활성화하고 resetPasswordEmail값을 리턴
	if (resetPasswordEmail) {
		$('#labelResetPasswordEmail').removeClass('text-danger');
		$('#labelResetPasswordEmail').addClass('text-success');
		$('#labelResetPasswordEmail').html('<i class="bi-check-lg me-1"></i> 이메일');
		return resetPasswordEmail;
	}
}

/******************
* 비밀번호 초기화 * 
*******************/
function resetPassword() {
	// 이메일 검증 (필수)
	var resetPasswordEmail = resetPasswordEmailCheck();
	if (!resetPasswordEmail) {
		$('#resetPasswordEmail').focus();
		return;
	}

	// ajax 통신을 위한 변수 설정
	url = '/api/account/resetPassword';
	type = 'POST';
	email = JSON.stringify({
		email: $('#resetPasswordEmail').val()
	});

	// 비밀번호 초기화 api 호출
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: url,
		type: type,
		data: email,
		cache: false,
		timeout: 60000,
		success: function(result) {
			if (result.status == 200) {
				alert($('#resetPasswordEmail').val() + '계정으로 초기화 비밀번호를 보내드렸습니다. 이메일을 확인해주세요');
				location.replace('');
			} else {
				$('#labelResetPasswordEmail').removeClass('text-success');
				$('#labelResetPasswordEmail').addClass('text-danger');
				$('#labelResetPasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> '+result.message);
			}
		},
		error: function() {
			$('#resetPasswordMessage').text('서버와의 통신에서 오류가 발생하였습니다');
		}
	});
}


/******************************************************************** 비밀번호 변경 관련 ***************************************************************************************/
/*********************
* 엔터키 입력 이벤트 *
**********************/
function enterChangePassword() {
	if (window.event.keyCode == 13) {
		changePassword();
	}
}

/**************
* 이메일 검증 *
***************/
// 비밀번호 변경의 이메일 입력값 변경 감지
function changePasswordEmailCheck() {
	$('#labelChangePasswordEmail').removeClass('text-danger text-success');
	// email 파라미터 검증
	var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var email = $('#changePasswordEmail').val();
	// email 값이 비어있을 때
	if (!email) {
		$('#labelChangePasswordEmail').addClass('text-danger');
		$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 이메일을 입력하세요');
		$('#changePasswordEmail').focus();
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#labelChangePasswordEmail').addClass('text-danger');
		$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		$('#changePasswordEmail').focus();
		return;
	}
	// 정상적인 이메일을 입력한 경우
	if (email) {
		$('#labelChangePasswordEmail').addClass('text-success');
		$('#labelChangePasswordEmail').html('<i class="bi-check me-1"></i> 이메일');
		return email;
	}
}

/*********************************
* 기존/초기화 비밀번호 검증 *
**********************************/
function oldPasswordCheck() {
	var oldPassword = $('#oldPassword').val();
	// 기존 비밀번호 값이 비어있을 때
	if (!oldPassword) {
		$('#labelOldPassword').removeClass('text-success');
		$('#labelOldPassword').addClass('text-danger');
		$('#labelOldPassword').html('<i class="bi-exclamation-triangle me-1"></i> 기존/초기화 비밀번호를 입력하세요');
		$('#oldPassword').focus();
		return;
	}
	$('#labelOldPassword').removeClass('text-danger');
	$('#labelOldPassword').addClass('text-success');
	$('#labelOldPassword').html('<i class="bi-check-lg me-1"></i> 기존/초기화 비밀번호');
	return oldPassword;
}

/*********************
* 신규 비밀번호 검증 *
**********************/
function newPasswordCheck() {
	// 신규 비밀번호 파라미터 검증
	var validatePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	var password = $('#newPassword').val();
	// 신규 비밀번호 값이 비어있을 때
	if (!password) {
		$('#labelNewPassword').removeClass('text-success');
		$('#labelNewPassword').addClass('text-danger');
		$('#labelNewPassword').html('<i class="bi-exclamation-triangle me-1"></i> 신규 비밀번호를 입력하세요');
		return;
	}
	// 신규 비밀번호 validate가 false 일 때
	if (validatePassword.test(password) == false) {
		$('#labelNewPassword').removeClass('text-success');
		$('#labelNewPassword').addClass('text-danger');
		$('#labelNewPassword').html('<i class="bi-exclamation-triangle me-1"></i> 신규 비밀번호는 8자 이상의 영문대소문자, 특수문자, 숫자를 포함해야합니다');
		return;
	} else {
		$('#labelNewPassword').removeClass('text-danger');
		$('#labelNewPassword').addClass('text-success');
		$('#labelNewPassword').html('<i class="bi-check-lg me-1"></i> 신규 비밀번호');
		// password와 passwordConfirm의 값이 다를 때
		var passwordConfirm = $('#newPasswordConfirm').val();
		if (password != passwordConfirm) {
			$('#labelNewPasswordConfirm').removeClass('text-success');
			$('#labelNewPasswordConfirm').addClass('text-danger');
			$('#labelNewPasswordConfirm').html('<i class="bi-exclamation-triangle me-1"></i> 신규 비밀번호와 동일하게 입력하세요');
			$('#newPasswordConfirm').focus();
			return;
		} else {
			$('#labelNewPasswordConfirm').removeClass('text-danger');
			$('#labelNewPasswordConfirm').addClass('text-success');
			$('#labelNewPasswordConfirm').html('<i class="bi-check-lg me-1"></i> 신규 비밀번호 확인');
			return password;
		}
	}
}

/****************
* 비밀번호 변경 * 
*****************/
function changePassword() {
	// 이메일 검증 (필수)
	var email = changePasswordEmailCheck();

	// 기존 비밀번호 검증 (필수)
	var oldPassword = oldPasswordCheck();

	// 신규 비밀번호 검증 (필수)
	var password = newPasswordCheck();

	// ajax 통신을 위한 변수 설정
	url = '/api/account/updatePassword';
	type = 'POST';
	payload = JSON.stringify({
		email: email,
		oldPassword: oldPassword,
		password: password
	});

	// 비밀번호 변경 api 호출
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: url,
		type: type,
		data: payload,
		cache: false,
		timeout: 60000,
		success: function(result) {
			if (result.status == 200) {	// 정상 변경
				alert(email + '계정의 비밀번호를 정상적으로 변경했습니다');
				location.replace('');
			} else if (result.status == 1004) {	// 계정이 존재하지 않는 경우
				$('#changePasswordMessage').text(result.message);
				$('#labelChangePasswordEmail').removeClass('text-success');
				$('#labelChangePasswordEmail').addClass('text-danger');
				$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 이메일');
				$('#changePasswordEmail').focus();
			} else if (result.status == 1005) {	// 기존 비밀번호가 일치하지 않는 경우
				$('#changePasswordMessage').text(result.message);
				$('#labelOldPassword').removeClass('text-success');
				$('#labelOldPassword').addClass('text-danger');
				$('#labelOldPassword').html('<i class="bi-exclamation-triangle me-1"></i> 기존/초기화 비밀번호');
				$('#oldPassword').focus();
			} else {	// 이외의 모든 예외처리
				$('#changePasswordMessage').text(result.message);
			}
		},
		error: function() {
			$('#changePasswordMessage').text('서버와의 통신에 실패했습니다');
		}
	});
}