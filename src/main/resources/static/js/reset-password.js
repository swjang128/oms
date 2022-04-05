/*********************
* 엔터키 입력 이벤트 *
**********************/
function enterResetPassword() {
	if (window.event.keyCode == 13) {		
		resetPassword();
	}
}

/******************
* 비밀번호 초기화 * 
*******************/
function resetPassword() {
	// ajax 통신을 위한 변수 설정
	url = '/api/account/resetPassword';
	type = 'POST';	
	email = JSON.stringify({
		email: $('#reset-email').val()
		});
		
	// 이메일 입력 체크
	if (!$('#reset-email').val()) {
		$('#reset-email-feedback').text('가입하신 이메일을 입력해주세요!');
		$('#reset-email').focus();
		return;
	}

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
				alert($('#reset-email').val()+'계정으로 초기화 비밀번호를 보내드렸습니다. 이메일을 확인해주세요');
				location.replace('');				
			} else {
				$('#reset-email-feedback').text(result.message);
			}
		},
		error: function() {
			$('#reset-email-feedback').text(result.message);
		}	
	});
}