/*****************
* 직급, 부서 조회 *
******************/
$(function() {
	alert('ready');
});

/**************
* 이메일 검증 *
***************/
function emailCheck() {
	// email 파라미터 검증
	var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var email = $('#create-email').val();
	
	// email 값이 비어있을 때
	if (!email) {
		$('#create-email-message').removeClass('text-success');
		$('#create-email-message').removeClass('text-danger');
		$('#create-email-message').html('이메일');
		return;
	}
	
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#create-email-message').removeClass('text-success');
		$('#create-email-message').addClass('text-danger');
		$('#create-email-message').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		return;
	}

	// 중복된 이메일인 경우
	if (email) {
		
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
				$('#create-email-message').removeClass('text-danger');
				$('#create-email-message').addClass('text-success');
				$('#create-email-message').html('<i class="bi-check-lg me-1"></i> 이메일');
			} else if (result.status == 1004) {	// 중복되는 이메일인 경우
				$('#create-email-message').removeClass('text-success');
				$('#create-email-message').addClass('text-danger');
				$('#create-email-message').html('<i class="bi-exclamation-triangle me-1"></i> 중복된 이메일');
			} 
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		}
	});
}