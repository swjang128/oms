/*********************
* 조건 드랍다운 열기 *
**********************/
function openDropdown() {
	// 부서 옵션이 존재하지 않을 때만 ajax 호출
	if ($('#dropdownDepartment option').length < 2) {
		// 부서 조회 후 셀렉스박스 옵션에 append
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department',
			type: 'GET',
			cache: false,
			datatype: 'json',
			success: function(result) {
				for (var d = 0; d < result.departmentList.length; d++) {
					$('#dropdownDepartment').append('<option value="'+result.departmentList[d].name+'">'+result.departmentList[d].name+'</option>');
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다.');
			}
		});
	}	
	
}

/*********************
* 기존 계정 정보 조회 *
**********************/
function updateAccountModal(id) {
	// 부서 옵션이 존재하지 않을 때만 ajax 호출
	if ($('#updateDepartment option').length < 2) {
		// 부서 조회 후 셀렉스박스 옵션에 append
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department',
			type: 'GET',
			cache: false,
			datatype: 'json',
			async: false,
			success: function(result) {
				for (var d = 0; d < result.departmentList.length; d++) {
					$('#updateDepartment').append('<option value="'+result.departmentList[d].name+'">'+result.departmentList[d].name+'</option>');
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다.');
			}
		});
	}

	// 직급 옵션이 존재하지 않을 때만 ajax 호출
	if ($('#updatePosition option').length < 2) {
		// 직급 조회 후 셀렉스박스 옵션에 append
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/position',
			type: 'GET',
			cache: false,
			datatype: 'json',
			async: false,
			success: function(result) {
				for (var d = 0; d < result.positionList.length; d++) {
					$('#updatePosition').append('<option value="'+result.positionList[d].name+'">'+result.positionList[d].name+'</option>');
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다.');
			}
		});
	}
	
	// 사용자 계정 정보 조회
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account/'+id,
		type: 'GET',
		cache: false,
		datatype: 'json',
		success: function(result) {
			$('#updatePhoto').val(result.account.photo);
			$('#updateName').val(result.account.name);
			$('#updateEmail').val(result.account.email);
			$('#updatePhone').val(result.account.phone);
			$('#updateEmergencyContact').val(result.account.emergencyContact);
			$('#updatePosition').val(result.account.position).prop('selected', true);
			$('#updateDepartment').val(result.account.department).prop('selected', true);
			$('#updateAddress').val(result.account.address);
			$('#updateAddressDetail').val(result.account.addressDetail);
			switch(result.account.role) {
				case 'ADMIN':
					$('#updateRoleAdmin').prop('checked', true);
					break;
				case 'MANAGER':
					$('#updateRoleManager').prop('checked', true);
					break;
				default:
					$('#updateRoleUser').prop('checked', true);
			}
			$('#updateBirthday').val(result.account.birthday);
			$('#updateHireDate').val(result.account.hireDate);
			// 현재 세션의 사용자와 일치하면 수정 버튼, 비밀번호 입력부 활성화
			if (result.account.id == $('#sessionId').val()) {
				$('#divUpdatePassword').removeClass('d-none');
				$('#updateAccountButton').removeClass('d-none');
			} else {
				$('#divUpdatePassword').addClass('d-none');
				$('#updateAccountButton').addClass('d-none');
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다');
		}
	});
	

}

/************
* 이름 검증 *
*************/
function updateNameCheck() {
	// name 파라미터 검증
	var validateName = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|]+$/;
	var name = $('#updateName').val();
	// name 값이 비어있을 때
	if (!name) {
		$('#labelUpdateName').removeClass('text-success');
		$('#labelUpdateName').addClass('text-danger');
		$('#labelUpdateName').html('<i class="bi-exclamation-triangle me-1"></i> 이름을 입력하세요');
		$('#updateName').focus();
		return;
	}
	// name validation이 false 일 때
	if (validateName.test(name) == false) {
		$('#labelUpdateName').removeClass('text-success');
		$('#labelUpdateName').addClass('text-danger');
		$('#labelUpdateName').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이름 형식');
		$('#updateName').focus();		
		return;
	} else {	// 올바른 name validation 일 때
		$('#labelUpdateName').removeClass('text-danger');
		$('#labelUpdateName').addClass('text-success');
		$('#labelUpdateName').html('<i class="bi-check-lg me-1"></i> 이름');
		return name;
	}
}

/**************
* 이메일 검증 *
***************/
function updateEmailCheck() {
	// email 파라미터 검증
	var validateEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	var email = $('#updateEmail').val();
	// email 값이 비어있을 때
	if (!email) {
		$('#labelUpdateEmail').removeClass('text-success');
		$('#labelUpdateEmail').addClass('text-danger');
		$('#labelUpdateEmail').html('<i class="bi-exclamation-triangle me-1"></i> 이메일을 입력하세요');
		$('#updateEmail').focus();
		return;
	}
	// email validate가 false 일 때
	if (validateEmail.test(email) == false) {
		$('#labelUpdateEmail').removeClass('text-success');
		$('#labelUpdateEmail').addClass('text-danger');
		$('#labelUpdateEmail').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 이메일 양식');
		$('#updateEmail').focus();
		return;
	}
	
	if (email) {
		$('#labelUpdateEmail').removeClass('text-danger');
		$('#labelUpdateEmail').addClass('text-success');
		$('#labelUpdateEmail').html('<i class="bi-check-lg me-1"></i> 이메일');
		return email;		
	}	
}

/**************
* 연락처 검증 *
***************/
function updatePhoneCheck() {
	// phone 파라미터 검증
	var validatePhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	var phone = $('#updatePhone').val();
	// phone 값이 비어있을 때
	if (!phone) {
		$('#labelUpdatePhone').removeClass('text-success');
		$('#labelUpdatePhone').addClass('text-danger');
		$('#labelUpdatePhone').html('<i class="bi-exclamation-triangle me-1"></i> 연락처를 입력하세요');
		$('#updatePhone').focus();
		return;
	}
	// phone validation이 false 일 때
	if (validatePhone.test(phone) == false) {
		$('#labelUpdatePhone').removeClass('text-success');
		$('#labelUpdatePhone').addClass('text-danger');
		$('#labelUpdatePhone').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 연락처 형식');
		$('#updatePhone').focus();
		return;
	} else {	// 올바른 phone validation 일 때
		$('#labelUpdatePhone').removeClass('text-danger');
		$('#labelUpdatePhone').addClass('text-success');
		$('#labelUpdatePhone').html('<i class="bi-check-lg me-1"></i> 연락처');
		phone = phone.replaceAll('-', '');
		return phone;
	}
}

/******************
* 비상연락처 검증 *
*******************/
function updateEmergencyContactCheck() {	
	// 비상연락처 파라미터 검증
	var validateEmergencyContact = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	var emergencyContact = $('#updateEmergencyContact').val();
	// 비상연락처 값이 비어있을 때
	if (!emergencyContact) {
		$('#labelUpdateEmergencyContact').removeClass('text-success');
		$('#labelUpdateEmergencyContact').removeClass('text-danger');
		$('#labelUpdateEmergencyContact').html('비상연락처 <span class="form-label-secondary">(선택)</span>');
		return;
	}
	// 비상연락처 validation이 false 일 때
	if (validateEmergencyContact.test(emergencyContact) == false) {
		$('#labelUpdateEmergencyContact').removeClass('text-success');
		$('#labelUpdateEmergencyContact').addClass('text-danger');
		$('#labelUpdateEmergencyContact').html('<i class="bi-exclamation-triangle me-1"></i> 잘못된 비상연락처 형식');
		$('#updateEmergencyContact').focus();
		return;
	}
	$('#labelUpdateEmergencyContact').removeClass('text-danger');
	$('#labelUpdateEmergencyContact').addClass('text-success');
	$('#labelUpdateEmergencyContact').html('<i class="bi-check-lg me-1"></i> 비상연락처 <span class="form-label-secondary">(선택)</span>');
	emergencyContact = emergencyContact.replaceAll('-', '');
	return emergencyContact;
}

/************
* 직급 검증 *
*************/
function updatePositionCheck() {
	var position = $('#updatePosition').val();
	// position 값이 비어있을 때
	if (!position) {
		$('#labelUpdatePosition').removeClass('text-success');
		$('#labelUpdatePosition').addClass('text-danger');
		$('#labelUpdatePosition').html('<i class="bi-exclamation-triangle me-1"></i> 직급을 선택하세요');
		$('#update-position').focus();
		return;
	} else {	// position을 선택했을 때
		$('#labelUpdatePosition').removeClass('text-danger');
		$('#labelUpdatePosition').addClass('text-success');
		$('#labelUpdatePosition').html('<i class="bi-check-lg me-1"></i> 직급');
		return position;
	}
}

/************
*  부서 검증 *
*************/
function updateDepartmentCheck() {
	var department = $('#updateDepartment').val();
	// department 값이 비어있을 때
	if (!department) {
		$('#labelUpdateDepartment').removeClass('text-success');
		$('#labelUpdateDepartment').addClass('text-danger');
		$('#labelUpdateDepartment').html('<i class="bi-exclamation-triangle me-1"></i> 부서를 선택하세요');
		$('#updateDepartment').focus();
		return;
	} else {	// department을 선택했을 때
		$('#labelUpdateDepartment').removeClass('text-danger');
		$('#labelUpdateDepartment').addClass('text-success');
		$('#labelUpdateDepartment').html('<i class="bi-check-lg me-1"></i> 부서');
		return department;
	}
}

/****************
*  주소 검색 API *
*****************/
function updateSearchAddress() {
	new daum.Postcode({
		oncomplete: function(data) {
			// address에 받아온 주소 넣기
			$('#updateAddress').val(data.address);
			$('#labelUpdateAddress').removeClass('text-danger');
			$('#labelUpdateAddress').addClass('text-success');
			$('#labelUpdateAddress').html('<i class="bi-check-lg me-1"></i> 주소');
			// 상세입력 focus
			document.querySelector('input[name=updateAddressDetail]').focus();
		}
	}).open();
}

/************
* 주소 검증 *
*************/
function updateAddressCheck() {
	var address = $('#updateAddress').val();
	// address 값이 비어있을 때
	if (!address) {
		$('#labelUpdateAddress').removeClass('text-success');
		$('#labelUpdateAddress').addClass('text-danger');
		$('#labelUpdateAddress').html('<i class="bi-exclamation-triangle me-1"></i> 주소를 입력하세요');
		$('#updateAddress').focus();
		return;
	} else {
		$('#labelUpdateAddress').removeClass('text-danger');
		$('#labelUpdateAddress').addClass('text-success');
		$('#labelUpdateAddress').html('<i class="bi-check-lg me-1"></i> 주소');
		return address;
	}
}

/****************
*  상세주소 검증 *
*****************/
function updateAddressDetailCheck() {
	var addressDetail = $('#updateAddressDetail').val();
	// addressDetail 값이 비어있을 때
	if (!addressDetail) {
		$('#labelUpdateAddressDetail').removeClass('text-success');
		$('#labelUpdateAddressDetail').removeClass('text-danger');
		$('#labelUpdateAddressDetail').html('상세주소 <span class="form-label-secondary">(선택)</span>');		
	} else {	// addressDetail 값을 입력했을 때
		$('#labelUpdateAddressDetail').removeClass('text-danger');
		$('#labelUpdateAddressDetail').addClass('text-success');
		$('#labelUpdateAddressDetail').html('<i class="bi-check-lg me-1"></i> 상세주소 <span class="form-label-secondary">(선택)</span>');
	}
	return addressDetail;
}

/************
*  권한 검증 *
*************/
function updateRoleCheck() {
	var role = $(' input[name="updateRole"]:checked ').val();
	// role을 선택하지 않았을 때
	if (!role) {
		$('#labelUpdateRole').removeClass('text-success');
		$('#labelUpdateRole').addClass('text-danger');
		$('#labelUpdateRole').html('<i class="bi-exclamation-triangle me-1"></i> 권한을 선택해주세요');
		$('#updateRole').focus();
		return;
	} else {	// role을 선택했을 때
		$('#labelUpdateRole').removeClass('text-danger');
		$('#labelUpdateRole').addClass('text-success');
		$('#labelUpdateRole').html('<i class="bi-check-lg me-1"></i> 권한');
		return role;
	}
}

/****************
* 비밀번호 검증 *
*****************/
function updatePasswordCheck() {
	// password 파라미터 검증
	var validatePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	var password = $('#updatePassword').val();
	// password 값이 비어있을 때
	if (!password) {
		$('#labelUpdatePassword').removeClass('text-success');
		$('#labelUpdatePassword').addClass('text-danger');
		$('#labelUpdatePassword').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호를 입력하세요');
		$('#updatePassword').focus();
		return;
	}
	// password validate가 false 일 때
	if (validatePassword.test(password) == false) {
		$('#labelUpdatePassword').removeClass('text-success');
		$('#labelUpdatePassword').addClass('text-danger');
		$('#labelUpdatePassword').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호는 8자 이상의 대,소문자, 특수문자, 숫자를 포함해야합니다');
		$('#updatePassword').focus();
		return;
	} else {
		$('#labelUpdatePassword').removeClass('text-danger');
		$('#labelUpdatePassword').addClass('text-success');
		$('#labelUpdatePassword').html('<i class="bi-check-lg me-1"></i> 비밀번호');
		// password와 passwordConfirm의 값이 다를 때
		var passwordConfirm = $('#updatePasswordConfirm').val();
		if (password != passwordConfirm) {
			$('#labelUpdatePasswordConfirm').removeClass('text-success');
			$('#labelUpdatePasswordConfirm').addClass('text-danger');
			$('#labelUpdatePasswordConfirm').html('<i class="bi-exclamation-triangle me-1"></i> 동일한 비밀번호를 입력하세요');
			$('#updatePasswordConfirm').focus();
			return;
		} else {
			$('#labelUpdatePasswordConfirm').removeClass('text-danger');
			$('#labelUpdatePasswordConfirm').addClass('text-success');
			$('#labelUpdatePasswordConfirm').html('<i class="bi-check-lg me-1"></i> 비밀번호 확인');
			return password;
		}
	}
}

/*********************
* 비밀번호 확인 검증 *
**********************/
function updatePasswordConfirmCheck() {
	var password = $('#updatePassword').val();
	var passwordConfirm = $('#updatePasswordConfirm').val();
	// passwordConfirm 값이 비어있을 때
	if (!passwordConfirm) {
		$('#labelUpdatePasswordConfirm').removeClass('text-success');
		$('#labelUpdatePasswordConfirm').addClass('text-danger');
		$('#labelUpdatePasswordConfirm').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호 확인을 입력하세요');
		$('#updatePasswordConfirm').focus();
		return;
	}
	// password와 passwordConfirm의 값이 다를 때
	if (password != passwordConfirm) {
		$('#labelUpdatePasswordConfirm').removeClass('text-success');
		$('#labelUpdatePasswordConfirm').addClass('text-danger');
		$('#labelUpdatePasswordConfirm').html('<i class="bi-exclamation-triangle me-1"></i> 동일한 비밀번호를 입력하세요');
		$('#updatePasswordConfirm').focus();
		return;
	} else {
		$('#labelUpdatePasswordConfirm').removeClass('text-danger');
		$('#labelUpdatePasswordConfirm').addClass('text-success');
		$('#labelUpdatePasswordConfirm').html('<i class="bi-check-lg me-1"></i> 비밀번호 확인');
		return passwordConfirm;
	}
}

/**************************
*  기본 비밀번호 사용 여부 *
***************************/
function updateDefaultPasswordCheck() {
	var defaultPassword = $('#updateDefaultPassword').is(':checked');
	// 기본 비밀번호를 사용하는 경우
	if (defaultPassword == true) {
		$('#divUpdatePassword').addClass('d-none');
		$('#divUpdatePasswordConfirm').addClass('d-none');
		$('#spanUpdateDefaultPasswordComma').removeClass('d-none');
		$('#spanUpdateDefaultPassword').removeClass('d-none');
		$('#updatePassword').val('');
		$('#updatePasswordConfirm').val('');
		return defaultPassword;		
	} else {	// 기본 비밀번호 체크해제
		$('#divUpdatePassword').removeClass('d-none');
		$('#divUpdatePasswordConfirm').removeClass('d-none');
		$('#spanUpdateDefaultPasswordComma').addClass('d-none');
		$('#spanUpdateDefaultPassword').addClass('d-none');
		$('#updatePassword').val('');
		$('#updatePasswordConfirm').val('');
		return;		
	}
}

/****************
*  생년월일 검증 *
*****************/
function updateBirthdayCheck() {
	var birthday = $('#updateBirthday').val();
	// birthday 값이 비어있을 때
	if (!birthday) {
		$('#labelUpdateBirthday').removeClass('text-success');
		$('#labelUpdateBirthday').removeClass('text-danger');
		$('#labelUpdateBirthday').html('생년월일 <span class="form-label-secondary">(선택)</span>');
	} else {	// birthday 값을 입력했을 때
		$('#labelUpdateBirthday').removeClass('text-danger');
		$('#labelUpdateBirthday').addClass('text-success');
		$('#labelUpdateBirthday').html('<i class="bi-check-lg me-1"></i> 생년월일');
	}
	return birthday;
}

/**************
*  입사일 검증 *
***************/
function updateHireDateCheck() {
	var hireDate = $('#updateHireDate').val();
	// hireDate 값이 비어있을 때
	if (!hireDate) {
		$('#labelUpdateHireDate').removeClass('text-success');
		$('#labelUpdateHireDate').removeClass('text-danger');
		$('#labelUpdateHireDate').html('입사일 <span class="form-label-secondary">(선택)</span>');		
	} else {	// addressDetail 값을 입력했을 때
		$('#labelUpdateHireDate').removeClass('text-danger');
		$('#labelUpdateHireDate').addClass('text-success');
		$('#labelUpdateHireDate').html('<i class="bi-check-lg me-1"></i> 입사일 <span class="form-label-secondary">(선택)</span>');
	}
	return hireDate;
}

/****************
*  비밀번호 검증 *
*****************/
function updatePasswordCheck() {
	var updatePassword = $('#updatePassword').val();
	// 비밀번호가 비어있을 때
	if (!updatePassword) {
		$('#labelUpdatePassword').removeClass('text-success');
		$('#labelUpdatePassword').addClass('text-danger');
		$('#labelUpdatePassword').html('<i class="bi-exclamation-triangle me-1"></i> 비밀번호를 입력하세요');
		$('#updatePassword').focus();
		return;
	} else {
		$('#labelUpdatePassword').removeClass('text-danger');
		$('#labelUpdatePassword').addClass('text-success');
		$('#labelUpdatePassword').html('<i class="bi-check-lg me-1"></i> 비밀번호');
		return updatePassword;
	}
	
}

/************
*  계정 수정 *
*************/
function updateAccount(id) {
	// 이름 검증 (필수)
	var name = updateNameCheck();
	if (!name) {
		return;
	}
	// 이메일 검증 (필수)
	var email = updateEmailCheck();
	if (!email) {
		return;
	}
	// 연락처 검증 (필수)
	var phone = updatePhoneCheck();
	if (!phone) {
		return;	
	}
	// 비상연락처 검증 (선택)
	var emergencyContact = updateEmergencyContactCheck();
	// 직급 검증 (필수)
	var position = updatePositionCheck();
	if (!position) {
		return;	
	}
	// 부서 검증 (필수)
	var department = updateDepartmentCheck();
	if (!department) {
		return;
	}
	// 주소 검증 (필수)
	var address = updateAddressCheck();
	if (!address) {
		return;
	}
	// 상세주소 검증 (선택)
	var addressDetail = updateAddressDetailCheck();
	// 권한 검증 (필수)	
	var role = updateRoleCheck();
	if (!role) {		
		return;	
	}
	// 생일 검증 (선택)
	var birthday = updateBirthdayCheck();
	// 입사일 검증 (선택)
	var hireDate = updateHireDateCheck();
	// 비밀번호 검증
	var password = updatePasswordCheck();
	if (!password) {
		return;
	}

	// 입력받은 모든 정보를 updateData(payload)에 추가
	var updateData = JSON.stringify({		
		hireDate: hireDate,
		birthday: birthday,
		password: password,
		role: role,
		addressDetail: addressDetail,
		address: address,
		position: position,
		department: department,
		emergencyContact: emergencyContact,
		phone: phone,
		email: email,
		name: name,
		id: id
	});
	
	// 계정 수정
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account',
		type: 'PUT',
		data: updateData,
		cache: false,
		success: function(result) {
			if (result.status == 200) {
				alert('계정 정보를 변경하였습니다');
				location.reload();
			} else if (result.status == 1015) {
				$('#labelUpdatePassword').removeClass('text-success');
				$('#labelUpdatePassword').addClass('text-danger');
				$('#labelUpdatePassword').html('<i class="bi-exclamation-triangle me-1"></i> '+result.message);
				$('#updatePassword').focus();
			} else {
				alert(result.message);
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		}
	});
}

/************
*  계정 삭제 *
*************/
function deleteAccount() {
	var payload = [];
	$('input:checkbox[name=accountRow]').each(function (id) {
		if ($(this).is(':checked') == true) {
			payload.push(id);
		}
	});
	
	// 계정 수정
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/account/'+payload,
		type: 'DELETE',
		cache: false,
		success: function(result) {
			if (result.status == 200) {
				alert('선택한 계정 정보를 삭제하였습니다');
				location.reload();
			} else {
				alert(result.message);
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다.');
		}
	});
	
}