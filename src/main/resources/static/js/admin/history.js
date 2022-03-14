/**************** 모달 제어부 ****************/
// 상위 접속내역를 선택하면 하위 접속내역들을 가져옴
$('#mainHistory').on('change', function() {
	type = 'GET';
	mainHistoryId = $('#mainHistory').val();
	
	// parentHistory 셀렉트박스의 하위 항목들을 모두 삭제
	$('#parentHistory').empty();
	
	// 접속내역 소분류 리스트 조회
	$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/history/'+mainHistoryId,
			type: type,			
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 조회 성공
							for (var c=0; c<result.childrenList.length; c++) {
								$('#parentHistory').append('<option value='+result.childrenList[c].id+'>'+
															result.childrenList[c].name+
															'</option>');
							}
							break;
						case 401:	// 파라미터 오류
							alert('잘못된 파라미터입니다.');
							break;
						case 403:	// 권한 없음
							alert('권한이 없습니다.');
							break;
						case 500:	// 내부 서버 오류
							alert('서버와의 통신을 실패하였습니다.');
							break;
						default:
							alert(result.message);
							break;
					}
			},
			error: function() {
				alert('서버와 통신이 불가능합니다.');
			}
		});
});

// 소분류를 선택하면 접속내역 목록을 가져옴
$('#parentHistory').on('change', function() {
	console.log('parent');
	$('#test').text($('#parentHistory').val());
	type = 'GET';
});


/*****************************
* 등록할 접속내역를 선택하는 모달 *
******************************/
var createModal = function createModal() {
	type = 'POST';
	selectHistory = '';
	// 어떤 접속내역를 추가할지 물어보는 모달창 활성화
	$('#selectModal').modal();
	
	// 접속내역 상세/수정을 한 다음에는 각 항목 초기화	
	/*if ($('#modal-title').text() !== '접속내역 등록') {
		$('#parentId').val('');
		$('#name').val('');
		$('#icon').val('');		
	}
	$("#modal-title").text("접속내역 등록");
	// 모달창 Open	
	$("#modal").modal();*/
};

/****************
* 메인 접속내역 모달 *
*****************/ 
function mainModal() {
	$('#mainModal').modal();
}

/****************
* 상위 접속내역 모달 *
*****************/ 
function parentModal() {
	$('#parentModal').modal();
}

/****************
* 하위 접속내역 모달 *
*****************/ 
function historyModal() {
	$('#historyModal').modal();
}

// 접속내역 상세/수정 모달
var updateModal = $(".updateModal:button").click(function() {
	type = 'PUT';
	
	// 접속내역의 상세 정보를 변수에 담기 (정규식 삭제)
	var row = $(this).parent().parent().children();
	var id = row.eq(0).text();
	var parentId = row.eq(1).text();
	var name = row.eq(2).text();
	var icon = row.eq(3).text();

	$("#modal-title").text("접속내역 수정");
	$("#submitLaw").text("수정");
	
	// 모달창에 접속내역의 상세정보 입력
	$('#id').val(id);
	$("#parentId").val(parentId);
	$("#name").val(name);
	$("#icon").val(icon);	
	
	// 모달창 Open
	$("#modal").modal();
});

/**************** document.ready *****************/
$(document).ready(function() {
	
/**************** DataTable 설정 ****************/
  	var historyTable = $('#table').DataTable({
		// 반응형웹 여부
		responsive: true,
		// 정규식 설정 (컬럼별)
		columnDefs: [
			{ targets: 0, data: 0, checkboxes: { selectRow: true } }	// 체크박스로 선택한 column[0]의 값을 데이터로 담음
		],
		// 용어 설정
		language: {
			emptyTable: "접속내역이 없습니다",
			zeroRecords: "접속내역이 없습니다",
			info: "검색된 접속내역: _TOTAL_ 개",
			infoEmpty: "검색된 결과가 없습니다",
			infoFiltered: "",
			search: "검색",
			select: {
				rows: {
					_: "%d개 선택됨",
					0: ""
				}
			},
			lengthMenu: "_MENU_　",
			paginate: {
				first: "처음",
				last: "마지막",
				next: "다음",
				previous: "이전"
			},
			aria: {
				sortAscending: ": 오름차순",
				sortDescending: ": 내림차순"
			}
		},
		// 검색 기능 설정
		search: {
			caseInsensitive: false,
			regex: true
		},
		// 기능들의 위치 설정
		/*dom: 'fBlrtip',*/
		dom: "<'row'<'col-sm-12 col-md-6' l B><'col-sm-12 col-md-6'f>>" +
        "<'row'<'col-sm-12' rt>>" +
        "<'row'<'col-sm-12 col-md-5' i><'col-sm-12 col-md-7' p>>",
		// 버튼
		buttons: [
			// 접속내역 등록
			{
				extends: 'create',
				text: '<i class="fas fa-pencil-alt fa-sm text-white-50"></i> 접속내역 등록',
				className: 'btn btn-sm btn-primary shadow-sm',
				action: function() {
					createModal();
				}
			},
			// 엑셀 다운로드
			{
				extend: 'excel',
				text: '<i class="fas fa-download fa-sm text-white-50"></i> 다운로드(Excel)',
				className: 'btn btn-sm btn-success shadow-sm',
				enabled: false,
				exportOptions: {
	                modifier: {
	                    page: 'all',
	                    selected: true,
	                    order: 'current'
	                }
            	}
            },
			// 선택한 접속내역 삭제 (DELETE) 
			{
				extends: 'delete',
				text: '<i class="fas fa-trash fa-sm text-white-50"></i> 선택 삭제',
				className: 'btn btn-sm btn-danger shadow-sm',
				enabled: false,
				action: function() {
					deleteHistory();				
				}
			}
		],
		// thead 고정 여부 설정 (고정)
		fixedHeader: {
			header: true
		},
		// row 선택 설정
		select: {
			style: 'multi+shift',
			selector: 'td:not(:last-child)'	// 해당 row의 마지막 td는 클릭해도 selected 상태가 되지 않는다.		
		},
		// 초기 정렬 설정
		order: [[1, 'asc']]
	});
	
	// 버튼 디자인 (BootStrap 디자인 가져오기)
	historyTable.buttons().container().appendTo( '.d-none d-inline-block btn btn-sm btn-primary shadow-sm' );
	
	// 특정 버튼을 매핑하는 기능 (트리거)(추후 기능 추가때 참조)
	$('.downloadLaw').click(function() {
		historyTable.button( '.buttons-excel' ).trigger();
	});
	
	// 선택된 row가 있으면 특정 버튼을 활성화
	historyTable.on( 'select deselect', function() {
		let selectedRows = historyTable.rows( { selected: true } ).count();
		historyTable.button(1).enable( selectedRows > 0 );	// 엑셀 버튼 활성화
		historyTable.button(2).enable( selectedRows > 0 );	// 삭제 버튼 활성화
	});
	
	/**************** 접속내역 CRUD ****************/
	// 접속내역 등록/수정 (Create/Update)
	$("#submit").click(function() {
		// 접속내역 정보 등록/수정 변수 담기
		if (type == 'POST') {	// 접속내역 등록 (POST)
			var historyData = JSON.stringify({
				parentId: $("#parentId").val(),
				name: $("#name").val(),
				icon: $("#icon").val(),
			});
		} else if (type == 'PUT') {		// 접속내역 수정 (PUT)
			var historyData = JSON.stringify({
				id: $("#id").val(),
				parentId: $("#parentId").val(),
				name: $("#name").val(),
				icon: $("#icon").val(),
			});
		}
		
		// 접속내역 등록/수정 처리
		$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/history',			
			type: type,
			data: historyData,
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 수정 성공
							/*alert('등록번호['+result.id+'] 접속내역 정보를 정상적으로 변경하였습니다.');*/
							alert('접속내역 정보를 정상적으로 변경하였습니다.');
							location.reload();
							break;
						case 201:	// 등록 성공
							alert('접속내역 정보를 정상적으로 등록하였습니다.');
							location.reload();
							break;
						case 401:	// 파라미터 오류
							alert('잘못된 파라미터입니다.');
							break;
						case 403:	// 권한 없음
							alert('권한이 없습니다.');
							break;
						case 500:	// 내부 서버 오류
							alert('서버와의 통신을 실패하였습니다.');
							break;
						default:
							alert(result.message);
							break;
					}
			},
			error: function() {
				alert('서버와 통신이 불가능합니다.');
			},
			complete: function() {
		//		location.reload();
			}
		});
	});
	
	// 접속내역 삭제 (Delete)	
	function deleteHistory() {
		// 삭제할 접속내역의 ID를 param에 담기
		var param = historyTable.columns().checkboxes.selected()[0];
		
		if (confirm("접속내역[ "+param+" ]을 삭제하시겠습니까?")) {
			$.ajax ({
				contentType: 'application/json',
				url: '/api/history/'+param,
				type: 'DELETE',
				success: function(result) {
					switch(result.status) {
						case 200:	// 삭제 성공
							alert('등록번호['+param+'] 접속내역을 정상적으로 삭제하였습니다.');
							location.reload();
							break;
						case 401:	// 파라미터 오류
							alert('잘못된 등록번호입니다.');
							break;
						case 403:	// 권한 없음
							alert('권한이 없습니다.');
							break;
						case 500:	// 내부 서버 오류
							alert('서버와의 통신을 실패하였습니다.');
							break;
						default:
							alert('오류가 발생하였습니다.');
							break;
					}
				},
				error: function() {
					alert('서버와 통신이 불가능합니다.');
				}
			});
		}
	}

});