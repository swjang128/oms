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
	var email = $('#changePasswordEmail').val();
	// email 값이 비어있을 때
	if (!email) {
		$('#labelChangePasswordEmail').removeClass('text-success');
		$('#labelChangePasswordEmail').addClass('text-danger');
		$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 이메일을 입력하세요');
		$('#changePasswordEmail').focus();
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#labelChangePasswordEmail').removeClass('text-success');
		$('#labelChangePasswordEmail').addClass('text-danger');
		$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		$('#changePasswordEmail').focus();
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
				$('#labelChangePasswordEmail').removeClass('text-danger');
				$('#labelChangePasswordEmail').addClass('text-success');
				$('#labelChangePasswordEmail').html('<i class="bi-check-lg me-1"></i> 이메일');
				return email;
			} else if (result.status == 200) {	// 없는 이메일인 경우
				$('#labelChangePasswordEmail').removeClass('text-success');
				$('#labelChangePasswordEmail').addClass('text-danger');
				$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 존재하지 않는 이메일입니다');
				$('#changePasswordEmail').focus();
				return;
			} else {
				$('#labelChangePasswordEmail').removeClass('text-success');
				$('#labelChangePasswordEmail').addClass('text-danger');
				$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 내부 서버 오류가 발생하였습니다');
				$('#changePasswordEmail').focus();
				return;
			}
		},
		error: function() {
			$('#labelChangePasswordEmail').removeClass('text-success');
			$('#labelChangePasswordEmail').addClass('text-danger');
			$('#labelChangePasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 서버와 통신할 수 없습니다');
			$('#changePasswordEmail').focus();
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
		$('#labelNewPassword').html('<i class="bi-exclamation-triangle me-1"></i> 신규 비밀번호는 8자 이상의 대,소문자, 특수문자, 숫자를 포함해야합니다');
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
	var oldPassword = 	oldPasswordCheck();
	
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
				$('#changePasswordMessage').text(result.message);
				$('#labelOldPassword').removeClass('text-success');
				$('#labelOldPassword').addClass('text-danger');
				$('#labelOldPassword').html('<i class="bi-exclamation-triangle me-1"></i> 기존/초기화 비밀번호');
				$('#oldPassword').focus();
			} else if (result.status == 1023) {	// 계정의 상태가 BLOCKED인 경우
				$('#changePasswordMessage').text(result.message);
			} else {	// 이외의 모든 예외처리
				$('#changePasswordMessage').text(result.message);
			}
		},
		error: function() {
			$('#changePasswordMessage').text(result.message);
		}	
	});
}

