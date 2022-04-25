// 사용자의 상태(userStatus) 변경
function updateUserStatus(userStatus) {	
	// ajax 통신을 위한 변수 설정
	let url = '/api/account/updateUserStatus';
	let type = 'PUT';
	let payload = JSON.stringify({
		email: $('#sessionEmail').text(),
		userStatus: userStatus
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
				$('#sessionUserStatus').removeClass('bg-danger bg-warning bg-info bg-dark bg-primary');
				switch (userStatus) {
					case 'ONLINE':
						$('#sessionUserStatus').text('온라인');
						$('#sessionUserStatus').addClass('bg-primary');
						break;
					case 'OFFLINE':
						$('#sessionUserStatus').text('오프라인');
						$('#sessionUserStatus').addClass('bg-danger');
						break;
					case 'AFK':
						$('#sessionUserStatus').text('자리비움');
						$('#sessionUserStatus').addClass('bg-warning');
						break;
					case 'BUSY':
						$('#sessionUserStatus').text('다른용무중');
						$('#sessionUserStatus').addClass('bg-info');
						break;
					default:
						$('#sessionUserStatus').text('알수없음');
						$('#sessionUserStatus').addClass('bg-dark');
				}
			} else {
				alert(result.message);
			}
		},
		error: function() {
			alert('서버와의 통신에 실패하였습니다');
		}	
	});
}
