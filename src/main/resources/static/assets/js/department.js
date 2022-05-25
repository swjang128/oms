// ======================= Department Modal Init =======================//
$(function() {
	// 등록시간, 수정시간 포맷 변경
	/*	$('td[name=departmentRegistTime]').each(function() {
			$(this).text(moment($(this).text()).format('YYYY-MM-DD'));
		});
		$('td[name=departmentUpdateTime]').each(function() {
			$(this).text(moment($(this).text()).format('YYYY-MM-DD'));
		});*/
});

// 부서 사용유무에 따라 체크박스 값 표시 변경
$('td[name=departmentUseYn]').each(function() {	
	if ($(this).children(':first').text() == 'Y') {		
		$(this).children(':eq(1)').children(':first').attr('checked', true);
	}
});

// ======================= Functions =======================//
/***********************
* 부서 저장 버튼 활성화 *
************************/
function activateDepartmentSaveButton(id) {
	// 사용여부 값 세팅
	var departmentUse = 'N';
	if ($('#departmentUseYn' + id).is(':checked') == true) {
		departmentUse = 'Y';
	}
	
	alert($('#departmentName' + id).val());
	// 부서명 또는 사용여부에 변화가 생기면 부서 저장 버튼이 활성화
	if ($('#departmentName' + id).val() != $('#departmentName' + id).parent().children(':first').text() ||
			$('#departmentUseYn' + id).parent().parent().children(':first').text() != departmentUse) {
		$('#departmentSaveButton' + id).prop('disabled', false);
	} else {
		$('#departmentSaveButton' + id).prop('disabled', true);
	}
}

/*******************
* 부서 등록 행 추가 *
********************/
function createDepartmentRow() {
	$('#createDepartmentRow').addClass('d-none');
	var ids = [];
	$('[name=departmentId]').each(function() {
		ids.push($(this).val());
	});
	var newId = Math.max(...ids) + 1;
	$('#departmentList').append(`<tr class="bg-success">
																<td class="table-column-pe-0"></td>
																<td class="table-column-ps-0">
																	<input type="text" class="form-control" id="departmentName`+ newId + `" placeholder="부서명을 입력하세요" onChange="departmentNameCheck(` + newId + `, this.value)">
																</td>
																<td name="departmentUseYn">
																	<div class="form-check form-switch">
																		<input class="form-check-input" type="checkbox" id="departmentUseYn`+ newId + `">
																		<label class="form-check-label" for="departmentUseYn`+ newId + `"></label>
																	</div>
																</td>
																<td>
																	<a class="btn btn-primary btn-sm" href="javascript:;" onClick="createDepartment(`+ newId + `)">
																		<i class="bi-plus-lg"></i> 등록
																	</a>
																	<a class="btn btn-danger btn-sm" href="javascript:;" onClick="deleteDepartmentRow(this)">
																		<i class="bi-trash"></i> 취소
																	</a>
																</td>
															</tr>`);
}

/**************
* 부서 행 삭제 *
***************/
function deleteDepartmentRow(row) {
	$(row).parent().parent().remove();
	$('#createDepartmentRow').removeClass('d-none');
}

/*******************
* 부서명 검증(등록) *
********************/
function departmentNameCheck(id, name) {
	var pattern = /\s/g;
	if (name == '' || name == undefined || name == null) {
		$('#departmentName' + id).focus();
		$('#departmentName' + id).select();
		return;
	}
	if (name.match(pattern)) {
		alert('부서명에 공백이 존재할 수 없습니다');
		$('#departmentName' + id).focus();
		$('#departmentName' + id).select();
		return;
	}
	// 중복되는 부서가 있는지 확인
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/department',
		type: 'GET',
		data: {
			name: name
		},
		cache: false,
		success: function(result) {
			if (result.departmentList.length > 0) {
				alert('중복되는 부서명이 존재합니다');
				$('#departmentName' + id).focus();
				$('#departmentName' + id).select();
				return;
			}
		},
		error: function() {
			alert('서버와의 통신에 실패했습니다');
			return;
		}
	});
	return name;
}

/*******************
* 부서명 검증(수정) *
********************/
function updateDepartmentNameCheck(id, name) {
	var pattern = /\s/g;
	if (name == '' || name == undefined || name == null) {
		$('#departmentName' + id).focus();
		$('#departmentName' + id).select();
		return;
	}
	if (name.match(pattern)) {
		alert('부서명에 공백이 존재할 수 없습니다');
		$('#departmentName' + id).focus();
		$('#departmentName' + id).select();
		return;
	}
	return name;
}

/************
* 부서 등록 *
*************/
function createDepartment(id) {
	let name = departmentNameCheck(id, $('#departmentName' + id).val());
	if (!name) {
		return;
	}
	let useYn = 'N';
	if ($('#departmentUseYn' + id).is(':checked') == true) {
		useYn = 'Y';
	}
	var createData = JSON.stringify({
		name: name,
		useYn: useYn
	});

	if (confirm('부서를 등록하시겠습니까?')) {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department',
			type: 'POST',
			data: createData,
			cache: false,
			success: function(result) {
				if (result.status == 201) {
					alert('부서를 정상적으로 등록하였습니다');
					location.reload();
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다');
			}
		});
	}
}

/************
* 부서 수정 *
*************/
function updateDepartment(id) {
	let name = updateDepartmentNameCheck(id, $('#departmentName' + id).val());
	if (!name) {
		return;
	}
	var useYn = 'N';
	if ($('#departmentUseYn' + id).is(':checked') == true) {
		useYn = 'Y';
	}
	var updateDate = JSON.stringify({
		id: id,
		name: name,
		useYn: useYn
	});

	if (confirm('변경한 부서 정보를 저장하시겠습니까?')) {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department',
			type: 'PUT',
			data: updateDate,
			cache: false,
			success: function(result) {
				if (result.status == 200) {
					alert('부서 정보를 정상적으로 변경하였습니다');
					location.reload();
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다');
			}
		});
	}
}
/************
* 부서 삭제 *
*************/
function deleteDepartment(param) {
	// 체크한 부서 삭제인 경우 (param이 undefined)
	if (param == undefined) {
		var param = [];
		/*$('input:checkbox[name=departmentId]').each(function() {
			if ($(this).is(':checked') == true) {
				param.push($(this).val());
			}
		});*/
		param.push($('#departmentTable').columns().checkboxes.selected()[0]);
	}
	// 부서 삭제
	if (confirm('선택한 부서를 삭제하시겠습니까?' + param)) {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department/' + param,
			type: 'DELETE',
			cache: false,
			success: function(result) {
				if (result.status == 200) {
					alert('선택한 부서를 삭제하였습니다');
					location.reload();
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다');
			}
		})
	}
}