// ======================= Ready =======================//
$(function() {
	alert('department ready');
	// 부서 테이블 설정
	// =======================================================
	HSCore.components.HSDatatables.init($('#departmentTable'), {
		dom: 'Bfrtip',
		select: {
			style: 'multi',
			selector: 'td:first-child input[type="checkbox"]',
			classMap: {
				checkAll: '#departmentCheckAll',
				counter: '#departmentCounter',
				counterInfo: '#departmentCounterInfo'
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
			totalQty: '#departmentWithPaginationInfoTotalQty'
		},
		search: '#departmentSearch',
		entries: '#departmentEntries',
		pageLength: 10,
		isResponsive: true,
		isShowPaging: false,
		pagination: 'departmentPagination',
		language: {
			zeroRecords: `<div class="text-center p-4">
              <img class="mb-3" src="/svg/illustrations/oc-error.svg" alt="Image Description" style="width: 10rem;" data-hs-theme-appearance="default">
              <img class="mb-3" src="/svg/illustrations-light/oc-error.svg" alt="Image Description" style="width: 10rem;" data-hs-theme-appearance="dark">
            <p class="mb-0">부서가 없습니다</p>
            </div>`
		}
	});
	// 부서 사용유무에 따라 체크박스 값 표시 변경
	$('td[name=departmentUseYn]').each(function() {
		if ($(this).children(':first').text() == 'Y') {
			$(this).children(':eq(1)').children(':first').prop('checked', true);
		}
	});
	// 등록시간, 수정시간 포맷 변경
	$('td[name=departmentRegistTime]').each(function() {
		$(this).text(moment($(this).text()).format('YYYY-MM-DD hh:mm:ss'));
	});
	$('td[name=departmentUpdateTime]').each(function() {
		$(this).text(moment($(this).text()).format('YYYY-MM-DD hh:mm:ss'));
	});
});

// ======================= Functions =======================//
/*******************
* 부서 등록 행 추가 *
********************/
function createDepartmentRow() {
	alert('행 추가');
}

/************
* 부서 수정 *
*************/
function updateDepartment(id) {	
	var name = $('#department'+id).parent().parent().parent().children(':eq(1)').children(':eq(1)').val();	
	var useYn = 'N';
	if ($('#departmentUseYn'+id).is(':checked') == true) {
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
			url: '/api/department/',
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
		})
	}
}
/************
* 부서 삭제 *
*************/
function deleteDepartment(param) {
	// 체크한 부서 삭제인 경우 (param이 undefined)
	if (param == undefined) {
		var param = [];
		$('input:checkbox[name=departmentId]').each(function() {
			if ($(this).is(':checked') == true) {
				param.push($(this).val());		
			}
		});		
	}
	// 부서 삭제
	if (confirm('선택한 부서를 삭제하시겠습니까?')) {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			url: '/api/department/'+param,
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