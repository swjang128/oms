// ======================= Ready =======================//
$(function() {
	// 직급 테이블 설정
	// =======================================================
	HSCore.components.HSDatatables.init($('#positionTable'), {
		dom: 'frtip',
		select: {
			style: 'multi',
			selector: 'td:first-child input[type="checkbox"]',
			classMap: {
				checkAll: '#positionCheckAll',
				counter: '#positionCounter',
				counterInfo: '#positionCounterInfo'
			}
		},
		columnDefs: [
			{
				targets: [0],
				orderable: false
			}
		],
		order: [],
		info: {
			totalQty: '#positionWithPaginationInfoTotalQty'
		},
		search: '#positionSearch',
		entries: '#positionEntries',
		pageLength: 10,
		isResponsive: true,
		isShowPaging: false,
		pagination: 'positionPagination',
		language: {
			zeroRecords: `<div class="text-center p-4">
              <img class="mb-3" src="/svg/illustrations/oc-error.svg" alt="Image Description" style="width: 10rem;" data-hs-theme-appearance="default">
              <img class="mb-3" src="/svg/illustrations-light/oc-error.svg" alt="Image Description" style="width: 10rem;" data-hs-theme-appearance="dark">
            <p class="mb-0">직급가 없습니다</p>
            </div>`
		}
	});
	// 직급 사용유무에 따라 체크박스 값 표시 변경
	$('td[name=positionUseYn]').each(function() {
		if ($(this).children(':first').text() == 'Y') {
			$(this).children(':eq(1)').children(':first').prop('checked', true);
		}
	});
	
	// 등록시간, 수정시간 포맷 변경
	$('td[name=positionRegistTime]').each(function() {
		$(this).text(moment($(this).text()).format('YYYY-MM-DD hh:mm:ss'));
	});
	$('td[name=positionUpdateTime]').each(function() {
		$(this).text(moment($(this).text()).format('YYYY-MM-DD hh:mm:ss'));
	});
	
	// 페이지내 표시개수를 변경하면 등록 버튼을 다시 보이게
	$('#positionEntries').change(function() {
		$('#createPositionRow').removeClass('d-none');
	});
	
	// 검색을 했을 때 등록 버튼을 다시 보이게
	$('#positionSearch').keyup(function() {
		$('#createPositionRow').removeClass('d-none');
	});
});

// ======================= Functions =======================//
/***********************
* 직급 저장 버튼 활성화 *
************************/
function activatePositionSaveButton(id) {
	// 사용여부 값 세팅
	var positionUse = 'N';
	if ($('#positionUseYn'+id).is(':checked') == true) {
		positionUse = 'Y';
	}
	// 직급명 또는 사용여부에 변화가 생기면 직급 저장 버튼이 활성화
	if ($('#positionName'+id).val() != $('#positionName'+id).parent().children(':first').text() ||
			$('#positionUseYn'+id).parent().parent().children(':first').text() != positionUse) {
		$('#positionSaveButton'+id).removeClass('d-none');
	} else {
		$('#positionSaveButton'+id).addClass('d-none');
	}
}

/*******************
* 직급 등록 행 추가 *
********************/
function createPositionRow() {
	$('#createPositionRow').addClass('d-none');
	var ids = [];
	$('[name=positionId]').each(function() {
		ids.push($(this).val());
	});
	var newId = Math.max(...ids)+1;
	$('#positionList').append(`<tr class="bg-success">
																<td class="table-column-pe-0"></td>
																<td class="table-column-ps-0">
																	<input type="text" class="border rounded" id="positionName`+newId+`" placeholder="직급명을 입력하세요" onChange="positionNameCheck(`+newId+`, this.value)">
																</td>
																<td name="positionUseYn">
																	<div class="form-check form-switch">
																		<input class="form-check-input" type="checkbox" id="positionUseYn`+newId+`">
																		<label class="form-check-label" for="positionUseYn`+newId+`"></label>
																	</div>
																</td>
																<td name="positionRegistTime"></td>
																<td name="positionUpdateTime"></td>
																<td>
																	<a class="btn btn-primary btn-sm" href="javascript:;" onClick="createPosition(`+newId+`)">
																		<i class="bi-plus-lg"></i> 등록
																	</a>
																	<a class="btn btn-danger btn-sm" href="javascript:;" onClick="deletePositionRow(this)">
																		<i class="bi-trash"></i> 취소
																	</a>
																</td>
															</tr>`);
}

/**************
* 직급 행 삭제 *
***************/
function deletePositionRow(row) {
	$(row).parent().parent().remove();
	$('#createPositionRow').removeClass('d-none');
}

/*******************
* 직급명 검증(등록) *
********************/
function positionNameCheck(id, name) {
	var pattern = /\s/g;
	if (name == '' || name == undefined || name == null) {
		$('#positionName'+id).focus();
		$('#positionName'+id).select();
		return;
	}
	if (name.match(pattern)) {
		alert('직급명에 공백이 존재할 수 없습니다');
		$('#positionName'+id).focus();
		$('#positionName'+id).select();
		return;
	}
	// 중복되는 직급이 있는지 확인
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: '/api/position',			
		type: 'GET',
		data: {
			name: name
		},
		cache: false,
		success: function(result) {
			if (result.positionList.length > 0) {					
				alert('중복되는 직급명이 존재합니다');
				$('#positionName'+id).focus();
				$('#positionName'+id).select();
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
* 직급명 검증(수정) *
********************/
function updatePositionNameCheck(id, name) {
	var pattern = /\s/g;
	if (name == '' || name == undefined || name == null) {
		$('#positionName'+id).focus();
		$('#positionName'+id).select();
		return;
	}
	if (name.match(pattern)) {
		alert('직급명에 공백이 존재할 수 없습니다');
		$('#positionName'+id).focus();
		$('#positionName'+id).select();
		return;
	}
	return name;
}

/************
* 직급 등록 *
*************/
function createPosition(id) {
	let name = positionNameCheck(id, $('#positionName'+id).val());
	if (!name) {		
		return;
	}
	let useYn = 'N';
	if ($('#positionUseYn'+id).is(':checked') == true) {
		useYn = 'Y';		
	}
	var createData = JSON.stringify({
		name: name,
		useYn: useYn
	});
	
	if (confirm('직급를 등록하시겠습니까?')) {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/position',
			type: 'POST',
			data: createData,
			cache: false,
			success: function(result) {
				if (result.status == 201) {
					alert('직급를 정상적으로 등록하였습니다');
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
* 직급 수정 *
*************/
function updatePosition(id) {	
	let name = updatePositionNameCheck(id, $('#positionName'+id).val());
	if (!name) {
		return;
	}
	var useYn = 'N';
	if ($('#positionUseYn'+id).is(':checked') == true) {
		useYn = 'Y';
	}	
	var updateDate = JSON.stringify({
		id: id,
		name: name,
		useYn: useYn
	});
	
	if (confirm('변경한 직급 정보를 저장하시겠습니까?')) {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/position',
			type: 'PUT',
			data: updateDate,
			cache: false,
			success: function(result) {
				if (result.status == 200) {
					alert('직급 정보를 정상적으로 변경하였습니다');
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
* 직급 삭제 *
*************/
function deletePosition(param) {
	// 체크한 직급 삭제인 경우 (param이 undefined)
	if (param == undefined) {
		var param = [];
		$('input:checkbox[name=positionId]').each(function() {
			if ($(this).is(':checked') == true) {
				param.push($(this).val());
			}
		});		
	}
	// 직급 삭제
	if (confirm('선택한 직급를 삭제하시겠습니까?')) {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/position/'+param,
			type: 'DELETE',
			cache: false,
			success: function(result) {
				if (result.status == 200) {
					alert('선택한 직급를 삭제하였습니다');
					location.reload();
				}
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다');
			}
		})
	}
}