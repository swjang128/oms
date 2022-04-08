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
		$('#labelResetPasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		return;
	}
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
				alert($('#resetPasswordEmail').val()+'계정으로 초기화 비밀번호를 보내드렸습니다. 이메일을 확인해주세요');
				location.replace('');				
			} else {
				$('#labelResetPasswordEmail').removeClass('text-success');
				$('#labelResetPasswordEmail').addClass('text-danger');
				$('#labelResetPasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i>'+result.message);
			}
		},
		error: function() {
			$('#labelResetPasswordEmail').removeClass('text-success');
			$('#labelResetPasswordEmail').addClass('text-danger');
			$('#labelResetPasswordEmail').html('<i class="bi-exclamation-triangle me-1"></i>'+result.message);
		}	
	});
}