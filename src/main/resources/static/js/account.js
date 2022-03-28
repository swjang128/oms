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
		return;
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
		return;
	} else {	// 올바른 phone validation 일 때
		$('#label-phone').removeClass('text-danger');
				$('#label-phone').addClass('text-success');
				$('#label-phone').html('<i class="bi-check-lg me-1"></i> 연락처');
	}
}

/******************
* 비상연락처 검증 *
*******************/
function emergencyCallCheck() {
	// emergencyCall 파라미터 검증
	var validateEmergencyCall = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	var emergencyCall = $('#account-emergency-call').val();
	
	// emergencyCall 값이 비어있을 때
	if (!emergencyCall) {
		$('#label-emergency-call').removeClass('text-success');
		$('#label-emergency-call').removeClass('text-danger');
		$('#label-emergency-call').html('비상연락처<span class="form-label-secondary">(선택)</span>');
		return;
	}
	
	// emergencyCall validation이 false 일 때
	if (validateEmergencyCall.test(emergencyCall) == false) {
		$('#label-emergency-call').removeClass('text-success');
		$('#label-emergency-call').addClass('text-danger');
		$('#label-emergency-call').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 비상연락처 형식');
		return;
	} else {	// 올바른 emergencyCall validation 일 때
		$('#label-emergency-call').removeClass('text-danger');
				$('#label-emergency-call').addClass('text-success');
				$('#label-emergency-call').html('<i class="bi-check-lg me-1"></i> 비상연락처<span class="form-label-secondary">(선택)</span>');
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
		return;
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
		return;
	} else {	// department을 선택했을 때
		$('#label-department').removeClass('text-danger');
				$('#label-department').addClass('text-success');
				$('#label-department').html('<i class="bi-check-lg me-1"></i> 부서');
	}
}