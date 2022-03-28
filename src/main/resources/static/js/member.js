/************
* 이름 검증 *
*************/
function nameCheck() {
	// name 파라미터 검증
	var validateName = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|]+$/;
	var name = $('#name').val();
	
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
	var email = $('#email').val();
	
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