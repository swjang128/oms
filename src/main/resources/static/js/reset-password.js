/*********************
* 엔터키 입력 이벤트 *
**********************/
function enter() {
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
		
	// Front-End Validation
	if (!$('#reset-email').val()) {
		$('#reset-email-feedback').text('가입하신 이메일을 입력해주세요!');
		$('#reset-email').focus();
		return;
	}

	// 로그인 컨트롤러 호출
	// 직원 등록/수정 처리
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: url,
		type: type,
		data: email,
		cache: false,
		timeout: 60000,
		success: function(result) {
			if (result.status == 200) {
				location.replace('');				
				alert('등록한 계정의 이메일로 임시 비밀번호를 전송하였습니다.');
			} else {
				$('#reset-email-feedback').text(result.message);
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		}	
	});
}