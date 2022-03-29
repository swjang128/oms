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
		$('#label-name').removeClass('text-danger');
		$('#label-name').html('이름');
		return;
	}
	// name validation이 false 일 때
	if (validateName.test(name) == false) {
		$('#label-name').removeClass('text-success');
		$('#label-name').addClass('text-danger');
		$('#label-name').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이름 형식');
	} else {	// 올바른 name validation 일 때
		$('#label-name').removeClass('text-danger');
		$('#label-name').addClass('text-success');
		$('#label-name').html('<i class="bi-check-lg me-1"></i> 이름');
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
		$('#label-email').removeClass('text-danger');
		$('#label-email').html('이메일');
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#label-email').removeClass('text-success');
		$('#label-email').addClass('text-danger');
		$('#label-email').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
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
			} else if (result.status == 1004) {	// 중복되는 이메일인 경우
				$('#label-email').removeClass('text-success');
				$('#label-email').addClass('text-danger');
				$('#label-email').html('<i class="bi-exclamation-triangle me-1"></i> 중복된 이메일');
			} else {
				alert('서버가 응답하지 않습니다.');	
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		}
	});
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
		$('#label-phone').removeClass('text-danger');
		$('#label-phone').html('연락처');
		return;
	}
	// phone validation이 false 일 때
	if (validatePhone.test(phone) == false) {
		$('#label-phone').removeClass('text-success');
		$('#label-phone').addClass('text-danger');
		$('#label-phone').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 연락처 형식');
	} else {	// 올바른 phone validation 일 때
		$('#label-phone').removeClass('text-danger');
		$('#label-phone').addClass('text-success');
		$('#label-phone').html('<i class="bi-check-lg me-1"></i> 연락처');
	}
}

/******************
* 비상연락처 검증 *
*******************/
function emergencyContactCheck() {
	// emergencyCall 파라미터 검증
	var validateEmergencyContact = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	var emergencyContact = $('#account-emergency-contact').val();
	// emergencyContact 값이 비어있을 때
	if (!emergencyContact) {
		$('#label-emergency-contact').removeClass('text-success');
		$('#label-emergency-contact').removeClass('text-danger');
		$('#label-emergency-contact').html('비상연락처 <span class="form-label-secondary">(선택)</span>');
		return;
	}
	// emergencyContact validation이 false 일 때
	if (validateEmergencyContact.test(emergencyContact) == false) {
		$('#label-emergency-contact').removeClass('text-success');
		$('#label-emergency-contact').addClass('text-danger');
		$('#label-emergency-contact').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 비상연락처 형식');		
	} else {	// 올바른 emergencyContact validation 일 때
		$('#label-emergency-contact').removeClass('text-danger');
		$('#label-emergency-contact').addClass('text-success');
		$('#label-emergency-contact').html('<i class="bi-check-lg me-1"></i> 비상연락처 <span class="form-label-secondary">(선택)</span>');
	}
}

/************
* 직급 검증 *
*************/
function positionCheck() {
	var position = $('#account-position').val();
	// position 값이 비어있을 때
	if (!position) {
		$('#label-position').removeClass('text-success');
		$('#label-position').removeClass('text-danger');
		$('#label-position').html('직급');
	} else {	// position을 선택했을 때
		$('#label-position').removeClass('text-danger');
		$('#label-position').addClass('text-success');
		$('#label-position').html('<i class="bi-check-lg me-1"></i> 직급');
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
		$('#label-department').removeClass('text-danger');
		$('#label-department').html('부서');
	} else {	// department을 선택했을 때
		$('#label-department').removeClass('text-danger');
		$('#label-department').addClass('text-success');
		$('#label-department').html('<i class="bi-check-lg me-1"></i> 부서');
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
}

/************
*  권한 검증 *
*************/
function roleCheck() {
	var role = $(' input[name="account-role"]:checked ').val();
	// role을 선택하지 않았을 때
	if (!role) {
		$('#label-role').removeClass('text-success');
		$('#label-role').removeClass('text-danger');
		$('#label-role').html('권한');		
	} else {	// role을 선택했을 때
		$('#label-role').removeClass('text-danger');
		$('#label-role').addClass('text-success');
		$('#label-role').html('<i class="bi-check-lg me-1"></i> 권한');		
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
		$('#label-password').removeClass('text-danger');
		$('#label-password').html('비밀번호');
		return;
	}
	// password validate가 false 일 때
	if (validatePassword.test(password) == false) {
		$('#label-password').removeClass('text-success');
		$('#label-password').addClass('text-danger');
		$('#label-password').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호는 8자 이상의 대,소문자, 특수문자, 숫자를 포함해야합니다');
	} else {
		$('#label-password').removeClass('text-danger');
		$('#label-password').addClass('text-success');
		$('#label-password').html('<i class="bi-check-lg me-1"></i> 비밀번호');
	}
}

/*********************
* 비밀번호 확인 검증 *
**********************/
function passwordConfirmCheck() {
	var password = $('#account-password').val();
	var passwordConfirm = $('#account-password-confirm').val();
	// passwordConfirm 값이 비어있을 때
	if (!passwordConfirm) {
		$('#label-password-confirm').removeClass('text-success');
		$('#label-password-confirm').removeClass('text-danger');
		$('#label-password-confirm').html('비밀번호 확인');
		return;
	}
	// password와 passwordConfirm의 값이 다를 때
	if (password != passwordConfirm) {
		$('#label-password-confirm').removeClass('text-success');
		$('#label-password-confirm').addClass('text-danger');
		$('#label-password-confirm').html('<i class="bi-exclamation-triangle me-1"></i> 동일한 비밀번호를 입력하세요');
	} else {
		$('#label-password-confirm').removeClass('text-danger');
		$('#label-password-confirm').addClass('text-success');
		$('#label-password-confirm').html('<i class="bi-check-lg me-1"></i> 비밀번호 확인');
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
		$('#span-password-default').removeClass('d-none');
		$('#account-password').val('');
		$('#account-password-confirm').val('');		
	} else {	// 기본 비밀번호 체크해제
		$('#div-password').removeClass('d-none');
		$('#div-password-confirm').removeClass('d-none');
		$('#span-password-default').addClass('d-none');
		$('#account-password').val('');
		$('#account-password-confirm').val('');
	}
	passwordCheck();
	passwordConfirmCheck();
}
 
/**************
*  입사일 검증 *
***************/
function hiredateCheck() {
	var hiredate = $('#account-hiredate').val();
	// addressDetail 값이 비어있을 때
	if (!hiredate) {
		$('#label-hiredate').removeClass('text-success');
		$('#label-hiredate').removeClass('text-danger');
		$('#label-hiredate').html('입사일 <span class="form-label-secondary">(선택)</span>');		
	} else {	// addressDetail 값을 입력했을 때
		$('#label-hiredate').removeClass('text-danger');
		$('#label-hiredate').addClass('text-success');
		$('#label-hiredate').html('<i class="bi-check-lg me-1"></i> 입사일 <span class="form-label-secondary">(선택)</span>');		
	}
}

/**************
*  등록 / 수정 *
***************/
function createOrUpdate() {
	alert('등록/수정');
}
