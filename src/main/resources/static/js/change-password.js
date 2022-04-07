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
function changePasswordEmailCheck() {
	// email 파라미터 검증
	var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var email = $('#change-password-email').val();
	// email 값이 비어있을 때
	if (!email) {
		$('#label-change-password-email').removeClass('text-success');
		$('#label-change-password-email').addClass('text-danger');
		$('#label-change-password-email').html('<i class="bi-exclamation-triangle me-1"></i> 이메일을 입력하세요');
		$('#change-password-email').focus();
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#label-change-password-email').removeClass('text-success');
		$('#label-change-password-email').addClass('text-danger');
		$('#label-change-password-email').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		$('#change-password-email').focus();
		return;
	}
	
	// 이메일 존재 유무 확인
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account/' + email,
		type: 'GET',
		cache: false,
		success: function(result) {
			if (result.status == 1009) {	// 이미 존재하는 이메일인 경우
				$('#label-change-password-email').removeClass('text-danger');
				$('#label-change-password-email').addClass('text-success');
				$('#label-change-password-email').html('<i class="bi-check-lg me-1"></i> 이메일');
				return email;
			} else if (result.status == 200) {	// 없는 이메일인 경우
				$('#label-change-password-email').removeClass('text-success');
				$('#label-change-password-email').addClass('text-danger');
				$('#label-change-password-email').html('<i class="bi-exclamation-triangle me-1"></i> 존재하지 않는 이메일입니다');
				$('#change-password-email').focus();
				return;
			} else {
				$('#label-change-password-email').removeClass('text-success');
				$('#label-change-password-email').addClass('text-danger');
				$('#label-change-password-email').html('<i class="bi-exclamation-triangle me-1"></i> 내부 서버 오류가 발생하였습니다');
				$('#change-password-email').focus();
				return;
			}
		},
		error: function() {
			$('#label-change-password-email').removeClass('text-success');
			$('#label-change-password-email').addClass('text-danger');
			$('#label-change-password-email').html('<i class="bi-exclamation-triangle me-1"></i> 서버와 통신할 수 없습니다');
			$('#change-password-email').focus();
			return;
		}
	});
	if (email) {
		return email;		
	}
}

/*********************************
* 기존/초기화 비밀번호 확인 검증 *
**********************************/
function oldPasswordCheck() {
	var oldPassword = $('#old-password').val();	
	// 기존 비밀번호 값이 비어있을 때
	if (!oldPassword) {
		$('#label-old-password').removeClass('text-success');
		$('#label-old-password').addClass('text-danger');
		$('#label-old-password').html('<i class="bi-exclamation-triangle me-1"></i> 기존/초기화 비밀번호를 입력하세요');
		$('#old-password').focus();
		return;
	}
	$('#label-old-password').removeClass('text-danger');
	$('#label-old-password').addClass('text-success');
	$('#label-old-password').html('<i class="bi-check-lg me-1"></i> 기존/초기화 비밀번호');
	return oldPassword;
}

/*********************
* 신규 비밀번호 검증 *
**********************/
function newPasswordCheck() {
	// 신규 비밀번호 파라미터 검증
	var validatePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	var password = $('#new-password').val();
	// 신규 비밀번호 값이 비어있을 때
	if (!password) {
		$('#label-new-password').removeClass('text-success');
		$('#label-new-password').addClass('text-danger');
		$('#label-new-password').html('<i class="bi-exclamation-triangle me-1"></i> 신규 비밀번호를 입력하세요');
		return;
	}
	// 신규 비밀번호 validate가 false 일 때
	if (validatePassword.test(password) == false) {
		$('#label-new-password').removeClass('text-success');
		$('#label-new-password').addClass('text-danger');
		$('#label-new-password').html('<i class="bi-exclamation-triangle me-1"></i> 신규 비밀번호는 8자 이상의 대,소문자, 특수문자, 숫자를 포함해야합니다');
		return;
	} else {
		$('#label-new-password').removeClass('text-danger');
		$('#label-new-password').addClass('text-success');
		$('#label-new-password').html('<i class="bi-check-lg me-1"></i> 신규 비밀번호');
		// password와 passwordConfirm의 값이 다를 때
		var passwordConfirm = $('#new-password-confirm').val();
		if (password != passwordConfirm) {
			$('#label-new-password-confirm').removeClass('text-success');
			$('#label-new-password-confirm').addClass('text-danger');
			$('#label-new-password-confirm').html('<i class="bi-exclamation-triangle me-1"></i> 신규 비밀번호와 동일하게 입력하세요');
			$('#new-password-confirm').focus();
			return;
		} else {
			$('#label-new-password-confirm').removeClass('text-danger');
			$('#label-new-password-confirm').addClass('text-success');
			$('#label-new-password-confirm').html('<i class="bi-check-lg me-1"></i> 신규 비밀번호 확인');
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
	var oldPassword = 	oldPasswordCheck();
	
	// 신규 비밀번호 검증 (필수)
	var password = newPasswordCheck();
	
	// ajax 통신을 위한 변수 설정
	url = '/api/account/changePassword';
	type = 'POST';
	payload = JSON.stringify({
		email: email,
		oldPassword: oldPassword,
		password: password
	});
	
	// 비밀번호 초기화 api 호출
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: url,
		type: type,
		data: payload,
		cache: false,
		timeout: 60000,
		success: function(result) {
			if (result.status == 200) {	// 정상 변경
				alert(email+'계정의 비밀번호를 정상적으로 변경했습니다');
				location.replace('');				
			} else if (result.status == 1005) {	// 기존 비밀번호가 일치하지 않는 경우
				$('#change-password-feedback').text(result.message);
				$('#label-old-password').removeClass('text-success');
				$('#label-old-password').addClass('text-danger');
				$('#label-old-password').html('<i class="bi-exclamation-triangle me-1"></i> 기존/초기화 비밀번호');
				$('#old-password').focus();
			} else if (result.status == 1023) {	// 계정의 상태가 BLOCKED인 경우
				$('#change-password-feedback').text(result.message);
			} else {	// 이외의 모든 예외처리
				$('#change-password-feedback').text(result.message);
			}
		},
		error: function() {
			$('#change-password-feedback').text(result.message);
		}	
	});
}

