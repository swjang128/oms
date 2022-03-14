/*******************************************************
* 비밀번호 입력창에서 엔터를 누르면 login() 펑션을 호출 * 
********************************************************/
function enterLogin() {
	if (window.event.keyCode == 13) {
		login();
	}
}

/**************************************************************************
* 계정 등록 모달1: 이메일 입력에서 엔터키를 누르면 checkEmail() 펑션 호출 * 
***************************************************************************/
function enterCheckEmail() {
	if (window.event.keyCode == 13) {
		checkEmail();
	}
}

/******************************
* 계정 등록 모달 1: 이메일 입력 *
*******************************/
function createEmail() {
	// modal-title 요소 설정
	$('#modal-title1').text('이메일 입력');
	$('#modal-title2').text('    [1/4]');
	// input 요소 설정
	$('#tr-email').removeClass('display-none');
	$('#tr-password').addClass('display-none');
	$('#tr-checkPassword').addClass('display-none');
	$('#tr-name').addClass('display-none');
	$('#tr-department').addClass('display-none');
	$('#tr-position').addClass('display-none');
	$('#tr-photo').addClass('display-none');
	$('#tr-phone').addClass('display-none');
	$('#tr-emergencyContact').addClass('display-none');
	$('#tr-birthday').addClass('display-none');
	$('#tr-hireDate').addClass('display-none');
	// modal-footer 요소 설정
	$('#modal-message').text('');
	$('#emailSubmit').removeClass('display-none');
	$('#privateCancel').addClass('display-none');
	$('#privateSubmit').addClass('display-none');
	$('#employeeCancel').addClass('display-none');
	$('#employeeSubmit').addClass('display-none');
	$('#passwordCancel').addClass('display-none');
	$('#passwordSubmit').addClass('display-none');
	// 모달창 Open
	$('#accountModal').modal();
}

/******************************************
* 계정 등록 모달 2: 개인정보 입력요소 호출 *
* ★ 이름											 				 *
* ★ 사진											 				 *
* ★ 연락처									 					 *
* ★ 비상연락처							  					 *
* ★ 생일															 *
*******************************************/
function createPrivate() {
	// modal-title 설정
	$('#modal-title1').text('개인정보 입력');	
	$('#modal-title2').text('    [2/4]');	
	// input 요소 설정
	$('#tr-name').removeClass('display-none');
	$('#tr-photo').removeClass('display-none');
	$('#tr-phone').removeClass('display-none');
	$('#tr-emergencyContact').removeClass('display-none');
	$('#tr-birthday').removeClass('display-none');
	$('#tr-email').addClass('display-none');
	$('#tr-department').addClass('display-none');
	$('#tr-position').addClass('display-none');
	$('#tr-hireDate').addClass('display-none');
	$('#tr-password').addClass('display-none');
	$('#tr-checkPassword').addClass('display-none');
	// modal-footer 설정
	$('#modal-message').text('');
	$('#emailSubmit').addClass('display-none');
	$('#privateCancel').removeClass('display-none');
	$('#privateSubmit').removeClass('display-none');
	$('#employeeCancel').addClass('display-none');
	$('#employeeSubmit').addClass('display-none');
	$('#passwordCancel').addClass('display-none');
	$('#passwordSubmit').addClass('display-none');
}

/******************************************
* 계정 등록 모달 3: 사원정보 입력요소 호출 *
* ★ 부서											 				 *
* ★ 직급											 				 *
* ★ 입사일									 					 *
*******************************************/
function createEmployee() {
	// modal-title 설정	
	$('#modal-title1').text('사원정보 입력');	
	$('#modal-title2').text('    [3/4]');	
	// input 요소 설정
	$('#tr-department').removeClass('display-none');
	$('#tr-position').removeClass('display-none');
	$('#tr-hireDate').removeClass('display-none');
	$('#tr-email').addClass('display-none');
	$('#tr-name').addClass('display-none');
	$('#tr-photo').addClass('display-none');
	$('#tr-phone').addClass('display-none');
	$('#tr-emergencyContact').addClass('display-none');
	$('#tr-birthday').addClass('display-none');
	$('#tr-password').addClass('display-none');
	$('#tr-checkPassword').addClass('display-none');
	// modal-footer 설정
	$('#modal-message').text('');
	$('#privateCancel').addClass('display-none');
	$('#privateSubmit').addClass('display-none');
	$('#employeeCancel').removeClass('display-none');
	$('#employeeSubmit').removeClass('display-none');
	$('#passwordCancel').addClass('display-none');
	$('#passwordSubmit').addClass('display-none');
}

/******************************************
* 계정 등록 모달 4: 비밀번호 입력요소 호출 *
* ★ 비밀번호									 				 *
* ★ 비밀번호 확인							 				 *
*******************************************/
function createPassword() {
	// modal-title 설정	
	$('#modal-title1').text('비밀번호 입력');	
	$('#modal-title2').text('    [4/4]');	
	// input 요소 설정
	$('#tr-password').removeClass('display-none');
	$('#tr-checkPassword').removeClass('display-none');
	$('#tr-email').addClass('display-none');
	$('#tr-name').addClass('display-none');
	$('#tr-photo').addClass('display-none');
	$('#tr-phone').addClass('display-none');
	$('#tr-emergencyContact').addClass('display-none');
	$('#tr-birthday').addClass('display-none');
	$('#tr-department').addClass('display-none');
	$('#tr-position').addClass('display-none');
	$('#tr-hireDate').addClass('display-none');
	// modal-footer 설정
	$('#modal-message').text('');
	$('#privateCancel').addClass('display-none');
	$('#privateSubmit').addClass('display-none');
	$('#employeeCancel').addClass('display-none');
	$('#employeeSubmit').addClass('display-none');
	$('#passwordCancel').removeClass('display-none');
	$('#passwordSubmit').removeClass('display-none');
}

/******************************
* 계정 등록 모달 1: 이메일 확인 *
*******************************/
function checkEmail() { 
	// email 파라미터 검증
	validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var email = $('#usermail').val();
	if (!email) {
		$('#modal-message').text('이메일을 입력하세요');
		$('#usermail').focus();
		return;
	}
	if (validateEmail.test(email) == false) {
		$('#modal-message').text('올바른 이메일 형식으로 입력해주세요');
		$('#usermail').focus();
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
			if (result.status == 200) {	// 중복되는 계정이 없으면 payload에 email 값을 추가하고 다음 절차로 진행
				createPrivate();
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		}
	});
}

/*********************************************
* 계정 등록 모달 2: 개인정보 입력 데이터 검증 *
* ★ 이름											 				      *
* ★ 사진											 				      *
* ★ 연락처									 					 	  *
* ★ 비상연락처							  					 	  *
* ★ 생일															 	  *
**********************************************/
function checkPrivate() {
	// 파라미터 검증
	var name = $('#name').val();
	var phone = $('#phone').val();
	var birthday = $('#birthday').val();

	if (!name) {
		$('#modal-message').text('이름을 입력하세요');
		$('#name').focus();
		return;
	}
	if (!phone) {
		$('#modal-message').text('연락처를 입력하세요');
		$('#phone').focus();
		return;
	}
	if (!birthday) {
		$('#modal-message').text('생일을 입력하세요!');
		$('#birthday').focus();
		return;
	}

	// 개인정보 검증이 끝나면 사원정보 입력 단계 호출
	createEmployee();
}

/*********************************************
* 계정 등록 모달 3: 사원정보 입력 데이터 검증 *
* ★ 부서											 					  *
* ★ 직급											 				      *
* ★ 입사일									 					 	  *
**********************************************/
function checkEmployee() {
	// 파라미터 검증
	var department = $('#department').val();
	var position = $('#position').val();
	var hireDate = $('#hireDate').val();

	if (!department) {
		$('#modal-message').text('부서를 선택하세요');
		$('#department').focus();
		return;
	}
	if (!position) {
		$('#modal-message').text('직책을 선택하세요');
		$('#position').focus();
		return;
	}
	if (!hireDate) {
		$('#modal-message').text('입사일을 입력하세요');
		$('#hireDate').focus();
		return;
	}
	
	// 사원정보 검증이 끝나면 비밀번호 입력 단계 호출
	createPassword();
}

/*********************************************
* 계정 등록 모달 4: 비밀번호 입력 데이터 검증 *
* ★ 비밀번호									 					  *
* ★ 비밀번호 확인							 				      *
**********************************************/
function checkPassword() {
	// 파라미터 검증
	var password = $('#pwd').val();
	var checkPassword = $('#checkPwd').val();
	var validatePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,64}$/;
	
	if (!password) {
		$('#modal-message').text('비밀번호를 입력하세요');
		$('#pwd').focus();
		return;
	}
	if (!validatePassword.test(password)) {
		$('#modal-message').text('비밀번호는 8자 이상, 64자 이하, 하나 이상의 대문자, 소문자, 숫자, 특수문자가 포함되어야 합니다.');
		$('#pwd').focus();
		return;	
	}
	if (!checkPassword || 
		password != checkPassword) {
		$('#modal-message').text('비밀번호와 비밀번호 확인의 값이 서로 다릅니다');
		$('#checkPwd').focus();
		return;
	}
	
	// 비밀번호 입력 및 검증까지 끝나면 계정 등록 이벤트 호출
	createAccount();
}

/**************** 계정 등록 *****************/
function createAccount() {
	// 입력받은 모든 정보를 accountData(payload)에 추가
	var accountData = JSON.stringify({
		department: $('#department').val(),
		position: $('#position').val(),
		photo: $('#photo').val(),		
		name: $('#name').val(),
		email: $('#usermail').val(),
		password: $('#pwd').val(),
		phone: $('#phone').val(),
		emergencyContact: $('#emergencyContact').val(),
		birthday: $('#birthday').val(),
		hireDate: $('#hireDate').val()
		});
	
	alert(accountData);
	$('#accountModal').modal('hide');
	
	// 직원 등록 처리
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account',
		type: 'POST',
		data: accountData,
		cache: false,
		success: function(result) {
			if (result.status == 200) {				
				alert('계정을 정상적으로 등록하였습니다');
			} else {
				alert('내부 서버 오류가 발생하였습니다');
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		},
		complete: function() {
			location.reload();
		}
	});
}

/**************** 로그인 *****************/
function login() {
	// Front-End Validation
	if (!$('#email').val()) {
		$('#message').text('이메일을 입력하세요!');
		$('#email').focus();
		return;
	}
	if (!$('#password').val()) {
		$('#message').text('비밀번호를 입력하세요!');
		$('#password').focus();
		return;
	}
	// 로그인 컨트롤러 호출
	$('#loginForm').submit();
}