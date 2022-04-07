/*************************
* remember-me Check *
**************************/
function rememberMeCheck() {
	var rememberMe = $('#remember-me').is(':checked');
	if (rememberMe == false) {
		$('#remember-me-message').removeClass('text-dark');
		$('#remember-me-message').addClass('text-muted');
	} else {
		$('#remember-me-message').removeClass('text-muted');
		$('#remember-me-message').addClass('text-dark');
	}
}

/*****************
* 부서, 직급 조회 *
******************/
function initAccountModal() {
	// 부서 옵션이 존재하지 않을 때만 ajax 호출
	if ($('#account-department option').length < 2) {
		// 부서 조회 후 셀렉스박스 옵션에 append
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department',
			type: 'GET',
			cache: false,
			datatype: 'json',
			success: function(result) {
				for (var d = 0; d < result.departmentList.length; d++) {
					$('#account-department').append('<option value="'+result.departmentList[d].name+'">'+result.departmentList[d].name+'</option>');
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다.');
			}
		});
	}

	// 직급 옵션이 존재하지 않을 때만 ajax 호출
	if ($('#account-position option').length < 2) {
		// 직급 조회 후 셀렉스박스 옵션에 append
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/position',
			type: 'GET',
			cache: false,
			datatype: 'json',
			success: function(result) {
				for (var d = 0; d < result.positionList.length; d++) {
					$('#account-position').append('<option value="'+result.positionList[d].name+'">'+result.positionList[d].name+'</option>');
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다.');
			}
		});
	}
}

/****************************
* 연락처 입력시 자동 하이픈 *
 ****************************/
const autoHyphen = (target) => {
	var number = target.value.replace(/[^0-9]/g, '');
	var phoneNumber = '';
	if (number.length < 4) {
		return number;
	} else if (number.length < 7) {
		phoneNumber += number.substr(0, 3);
		phoneNumber += '-';
		phoneNumber += number.substr(3);
	} else if (number.length < 11) {
		phoneNumber += number.substr(0, 3);
		phoneNumber += '-';
		phoneNumber += number.substr(3, 3);
		phoneNumber += '-';
		phoneNumber += number.substr(6);
	} else {
		phoneNumber += number.substr(0, 3);
		phoneNumber += '-';
		phoneNumber += number.substr(3, 4);
		phoneNumber += '-';
		phoneNumber += number.substr(7);
	}
	target.value = phoneNumber;
}

/************
* 이름 검증 *
*************/
function nameCheck() {
	// name 파라미터 검증
	var validateName = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|]+$/;
	var name = $('#account-name').val();
	// name 값이 비어있을 때
	if (!name) {
		$('#label-name').removeClass('text-success');
		$('#label-name').addClass('text-danger');
		$('#label-name').html('<i class="bi-exclamation-triangle me-1"></i> 이름을 입력하세요');
		$('#account-name').focus();
		return;
	}
	// name validation이 false 일 때
	if (validateName.test(name) == false) {
		$('#label-name').removeClass('text-success');
		$('#label-name').addClass('text-danger');
		$('#label-name').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이름 형식');
		$('#account-name').focus();		
		return;
	} else {	// 올바른 name validation 일 때
		$('#label-name').removeClass('text-danger');
		$('#label-name').addClass('text-success');
		$('#label-name').html('<i class="bi-check-lg me-1"></i> 이름');
		return name;
	}
}

/**************
* 이메일 검증 *
***************/
function emailCheck() {
	// email 파라미터 검증
	var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var email = $('#account-email').val();
	// email 값이 비어있을 때
	if (!email) {
		$('#label-email').removeClass('text-success');
		$('#label-email').addClass('text-danger');
		$('#label-email').html('<i class="bi-exclamation-triangle me-1"></i> 이메일을 입력하세요');
		$('#account-email').focus();
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#label-email').removeClass('text-success');
		$('#label-email').addClass('text-danger');
		$('#label-email').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		$('#account-email').focus();
		return;
	}
	// 이메일 중복 확인
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account/' + email,
		type: 'GET',
		cache: false,
		success: function(result) {
			$('#modal-message').text(result.message);
			if (result.status == 200) {	// 생성 가능한 이메일인 경우
				$('#label-email').removeClass('text-danger');
				$('#label-email').addClass('text-success');
				$('#label-email').html('<i class="bi-check-lg me-1"></i> 이메일');
				return email;				
			} else if (result.status == 1009) {	// 중복되는 이메일인 경우
				$('#label-email').removeClass('text-success');
				$('#label-email').addClass('text-danger');
				$('#label-email').html('<i class="bi-exclamation-triangle me-1"></i> 중복된 이메일');
				$('#account-email').focus();				
				return;
			} else {
				alert('서버가 응답하지 않습니다.');
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
	var validatePhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	var phone = $('#account-phone').val();
	// phone 값이 비어있을 때
	if (!phone) {
		$('#label-phone').removeClass('text-success');
		$('#label-phone').addClass('text-danger');
		$('#label-phone').html('<i class="bi-exclamation-triangle me-1"></i> 연락처를 입력하세요');
		$('#account-phone').focus();
		return;
	}
	// phone validation이 false 일 때
	if (validatePhone.test(phone) == false) {
		$('#label-phone').removeClass('text-success');
		$('#label-phone').addClass('text-danger');
		$('#label-phone').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 연락처 형식');
		$('#account-phone').focus();
		return;
	} else {	// 올바른 phone validation 일 때
		$('#label-phone').removeClass('text-danger');
		$('#label-phone').addClass('text-success');
		$('#label-phone').html('<i class="bi-check-lg me-1"></i> 연락처');
		phone = phone.replaceAll('-', '');
		return phone;
	}
}

/******************
* 비상연락처 검증 *
*******************/
function emergencyContactCheck() {	
	// 비상연락처 파라미터 검증
	var validateEmergencyContact = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	var emergencyContact = $('#account-emergency-contact').val();
	// 비상연락처 값이 비어있을 때
	if (!emergencyContact) {
		$('#label-emergency-contact').removeClass('text-success');
		$('#label-emergency-contact').removeClass('text-danger');
		$('#label-emergency-contact').html('비상연락처 <span class="form-label-secondary">(선택)</span>');
		return;
	}
	// 비상연락처 validation이 false 일 때
	if (validateEmergencyContact.test(emergencyContact) == false) {
		$('#label-emergency-contact').removeClass('text-success');
		$('#label-emergency-contact').addClass('text-danger');
		$('#label-emergency-contact').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 비상연락처 형식');
		$('#account-emergency-contact').focus();
		return;
	}
	$('#label-emergency-contact').removeClass('text-danger');
	$('#label-emergency-contact').addClass('text-success');
	$('#label-emergency-contact').html('<i class="bi-check-lg me-1"></i> 비상연락처 <span class="form-label-secondary">(선택)</span>');
	emergencyContact = emergencyContact.replaceAll('-', '');
	return emergencyContact;
}

/************
* 직급 검증 *
*************/
function positionCheck() {
	var position = $('#account-position').val();
	// position 값이 비어있을 때
	if (!position) {
		$('#label-position').removeClass('text-success');
		$('#label-position').addClass('text-danger');
		$('#label-position').html('<i class="bi-exclamation-triangle me-1"></i> 직급을 선택하세요');
		$('#account-position').focus();
		return;
	} else {	// position을 선택했을 때
		$('#label-position').removeClass('text-danger');
		$('#label-position').addClass('text-success');
		$('#label-position').html('<i class="bi-check-lg me-1"></i> 직급');
		return position;
	}
}

/************
*  부서 검증 *
*************/
function departmentCheck() {
	var department = $('#account-department').val();
	// department 값이 비어있을 때
	if (!department) {
		$('#label-department').removeClass('text-success');
		$('#label-department').addClass('text-danger');
		$('#label-department').html('<i class="bi-exclamation-triangle me-1"></i> 부서를 선택하세요');
		$('#account-department').focus();
		return;
	} else {	// department을 선택했을 때
		$('#label-department').removeClass('text-danger');
		$('#label-department').addClass('text-success');
		$('#label-department').html('<i class="bi-check-lg me-1"></i> 부서');
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
			$('#account-address').val(data.address);
			$('#label-address').removeClass('text-danger');
			$('#label-address').addClass('text-success');
			$('#label-address').html('<i class="bi-check-lg me-1"></i> 주소');
			// 상세입력 focus
			document.querySelector('input[name=account-address-detail]').focus();
		}
	}).open();
}

/************
* 주소 검증 *
*************/
function addressCheck() {
	var address = $('#account-address').val();
	// address 값이 비어있을 때
	if (!address) {
		$('#label-address').removeClass('text-success');
		$('#label-address').addClass('text-danger');
		$('#label-address').html('<i class="bi-exclamation-triangle me-1"></i> 주소를 입력하세요');
		$('#account-address').focus();
		return;
	} else {
		$('#label-address').removeClass('text-danger');
		$('#label-address').addClass('text-success');
		$('#label-address').html('<i class="bi-check-lg me-1"></i> 주소');
		return address;
	}
}

/****************
*  상세주소 검증 *
*****************/
function addressDetailCheck() {
	var addressDetail = $('#account-address-detail').val();
	// addressDetail 값이 비어있을 때
	if (!addressDetail) {
		$('#label-address-detail').removeClass('text-success');
		$('#label-address-detail').removeClass('text-danger');
		$('#label-address-detail').html('상세주소 <span class="form-label-secondary">(선택)</span>');		
	} else {	// addressDetail 값을 입력했을 때
		$('#label-address-detail').removeClass('text-danger');
		$('#label-address-detail').addClass('text-success');
		$('#label-address-detail').html('<i class="bi-check-lg me-1"></i> 상세주소 <span class="form-label-secondary">(선택)</span>');
	}
	return addressDetail;
}

/************
*  권한 검증 *
*************/
function roleCheck() {
	var role = $(' input[name="account-role"]:checked ').val();
	// role을 선택하지 않았을 때
	if (!role) {
		$('#label-role').removeClass('text-success');
		$('#label-role').addClass('text-danger');
		$('#label-role').html('<i class="bi-exclamation-triangle me-1"></i> 권한을 선택해주세요');
		$('#account-role').focus();
		return;
	} else {	// role을 선택했을 때
		$('#label-role').removeClass('text-danger');
		$('#label-role').addClass('text-success');
		$('#label-role').html('<i class="bi-check-lg me-1"></i> 권한');
		return role;
	}
}

/****************
* 비밀번호 검증 *
*****************/
function passwordCheck() {
	// password 파라미터 검증
	var validatePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	var password = $('#account-password').val();
	// password 값이 비어있을 때
	if (!password) {
		$('#label-password').removeClass('text-success');
		$('#label-password').addClass('text-danger');
		$('#label-password').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호를 입력하세요');
		$('#account-password').focus();
		return;
	}
	// password validate가 false 일 때
	if (validatePassword.test(password) == false) {
		$('#label-password').removeClass('text-success');
		$('#label-password').addClass('text-danger');
		$('#label-password').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호는 8자 이상의 대,소문자, 특수문자, 숫자를 포함해야합니다');
		$('#account-password').focus();
		return;
	} else {
		$('#label-password').removeClass('text-danger');
		$('#label-password').addClass('text-success');
		$('#label-password').html('<i class="bi-check-lg me-1"></i> 비밀번호');
		// password와 passwordConfirm의 값이 다를 때
		var passwordConfirm = $('#account-password-confirm').val();
		if (password != passwordConfirm) {
			$('#label-password-confirm').removeClass('text-success');
			$('#label-password-confirm').addClass('text-danger');
			$('#label-password-confirm').html('<i class="bi-exclamation-triangle me-1"></i> 동일한 비밀번호를 입력하세요');
			$('#account-password-confirm').focus();
			return;
		} else {
			$('#label-password-confirm').removeClass('text-danger');
			$('#label-password-confirm').addClass('text-success');
			$('#label-password-confirm').html('<i class="bi-check-lg me-1"></i> 비밀번호 확인');
			return password;
		}
	}
}

/**************************
*  기본 비밀번호 사용 여부 *
***************************/
function defaultPasswordCheck() {
	var defaultPassword = $('#account-default-password').is(':checked');
	// 기본 비밀번호를 사용하는 경우
	if (defaultPassword == true) {
		$('#div-password').addClass('d-none');
		$('#div-password-confirm').addClass('d-none');
		$('#span-default-password-comma').removeClass('d-none');
		$('#span-default-password').removeClass('d-none');
		$('#account-password').val('');
		$('#account-password-confirm').val('');
		return defaultPassword;		
	} else {	// 기본 비밀번호 체크해제
		$('#div-password').removeClass('d-none');
		$('#div-password-confirm').removeClass('d-none');
		$('#span-default-password-comma').addClass('d-none');
		$('#span-default-password').addClass('d-none');
		$('#account-password').val('');
		$('#account-password-confirm').val('');
		return;		
	}
}

/****************
*  생년월일 검증 *
*****************/
function birthdayCheck() {
	var birthday = $('#account-birthday').val();
	// addressDetail 값이 비어있을 때
	if (!birthday) {
		$('#label-birthday').removeClass('text-success');
		$('#label-birthday').removeClass('text-danger');
		$('#label-birthday').html('생년월일 <span class="form-label-secondary">(선택)</span>');
	} else {	// addressDetail 값을 입력했을 때
		$('#label-birthday').removeClass('text-danger');
		$('#label-birthday').addClass('text-success');
		$('#label-birthday').html('<i class="bi-check-lg me-1"></i> 생년월일');
	}
	return birthday;
}

/**************
*  입사일 검증 *
***************/
function hireDateCheck() {
	var hireDate = $('#account-hire-date').val();
	// hireDate 값이 비어있을 때
	if (!hireDate) {
		$('#label-hire-date').removeClass('text-success');
		$('#label-hire-date').removeClass('text-danger');
		$('#label-hire-date').html('입사일 <span class="form-label-secondary">(선택)</span>');		
	} else {	// addressDetail 값을 입력했을 때
		$('#label-hire-date').removeClass('text-danger');
		$('#label-hire-date').addClass('text-success');
		$('#label-hire-date').html('<i class="bi-check-lg me-1"></i> 입사일 <span class="form-label-secondary">(선택)</span>');
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
	emergencyContactCheck();
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
	addressDetailCheck();
	// 권한 검증 (필수)	
	var role = roleCheck();
	if (!role) {		
		return;	
	}
	// 비밀번호 검증 (필수)
	var defaultPassword = $('#account-default-password').is(':checked');
	if (defaultPassword == true) { // 기본 비밀번호를 사용하는 경우
		var password = $('#span-default-password').text();
	} else { // 일반 비밀번호를 사용하는 경우
		var password = passwordCheck();
		if (!password) {
			return;
		}
	}
	// 생일 검증 (선택)
	birthdayCheck();
	// 입사일 검증 (선택)
	hireDateCheck();

	// 입력받은 모든 정보를 accountData(payload)에 추가
	var accountData = JSON.stringify({
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
	
	alert(accountData);
	
	// 직원 등록 처리
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account',
		type: 'POST',
		data: accountData,
		cache: false,
		success: function(result) {
			if (result.status == 200) {				
				alert('계정을 정상적으로 등록하였습니다');
			} else {
				alert('내부 서버 오류가 발생하였습니다');
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		},
		complete: function() {
			location.reload();
		}
	});
}