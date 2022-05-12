/**************
* 모달 제어부 *
***************/
// 상위 일감 목록 조회
function parentTaskList() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: '/api/task/'+'0',
		type: 'GET',			
		cache: false,
		success: function(result) {
			switch(result.status) {
					case 200:	// 조회 성공
						for (var c=0; c<result.taskList.length; c++) {
							$('#parent-task').append('<option value='+result.taskList[c].id+'>'+result.taskList[c].name+'</option>');							
						}
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
}

// 직원 목록 조회
function memberList() {
	if ( $('#manager option').length==0 || $('#worker option').length==0 ) {
		$('#manager').empty();
		$('#manager').append('<option value='+0+'>없음</option>');
		$('#worker').empty();
		$('#worker').append('<option value='+0+'>없음</option>');
		$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/member',
			type: 'GET',			
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 조회 성공
							for (var c=0; c<result.memberList.length; c++) {
								$('#manager').append('<option value='+result.memberList[c].id+'>'+result.memberList[c].name+'</option>');
								$('#worker').append('<option value='+result.memberList[c].id+'>'+result.memberList[c].name+'</option>');							
							}
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
	}
	
}

// 업무 등록 모달
var createModal = function createModal() {
	type = 'POST';	
	// 상품 상세/수정을 한 다음에는 각 항목 초기화	
	if ($('#modal-title').text() !== '업무 등록') {
		$('#title').val('');
		$('#content').val('');
	}
	$('#modal-title').text('업무 등록');
	$('#modal-message').text( $('#sessionName').text() );
	// 상위 일감 목록 조회
	parentTaskList();
	// 직원 목록 조회
	memberList();
	// 모달창 Open	
	$('#task-modal').modal();
	$('#modal-message').text();
};

// 업무 상세/수정 모달
var updateModal = $(".updateModal:button").click(function() {
	type = 'PUT';
	
	// 업무의 상세 정보를 변수에 담기 (정규식 삭제)
	var row = $(this).parent().parent().children();
	var id = row.eq(0).text();
	var parentId = row.eq(1).text();
	var title = row.eq(2).text();
	var content = row.eq(3).text();

	$("#modal-title").text("업무 수정");
	$("#submit").text("수정");
	
	// 모달창에 업무의 상세정보 입력
	$('#id').val(id);
	$("#parentId").val(parentId);
	$("#title").val(title);
	$("#content").val(content);	
	
	// 모달창 Open
	$("#task-modal").modal();
});


/****************
* 메인 업무 모달 *
*****************/ 
function mainModal() {
	$('#mainModal').modal();
	$('#maintask').empty();
	rootId = 0;
	// 메인 업무 목록 조회 (Java Script AJAX)
	xhr = new XMLHttpRequest();	// XMLHttpRequest 객체 생성
	xhr.open('GET', '/api/task/'+rootId, true);		// 요청을 보낼 method, url, 비동기여부 설정
	xhr.send();		// AJAX 요청 전송
	xhr.onload = () => {		// AJAX 통신 후 작업		
		if (xhr.status==200) {	// 통신 성공
			response = JSON.parse(xhr.response);
			document.getElementById('modal-message').innerHTML = response.message;			
		} else {	// 통신 성공이 아닌 경우
			document.getElementById('modal-message').innerHTML = '통신 실패: '+xhr.status;
		}
	}
	
	// 모달창 Open
	$("#task-modal").modal();
	
	// 메인 업무 목록 조회 (jquery AJAX)
/*	$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/task/'+0,			
			type: 'GET',			
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 조회 성공
							for (var c=0; c<result.taskList.length; c++) {
								$('#maintask').append('<option value='+result.taskList[c].id+'>'+
															result.taskList[c].name+
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
		});*/
}










/**************** document.ready *****************/
$(document).ready(function() {
	
/**************** DataTable 설정 ****************/
  	var taskTable = $('#table').DataTable({
		// 반응형웹 여부
		responsive: true,
		// 정규식 설정 (컬럼별)
		columnDefs: [
			{ targets: 0, data: 0, checkboxes: { selectRow: true } }	// 체크박스로 선택한 column[0]의 값을 데이터로 담음
		],
		// 용어 설정
		language: {
			emptyTable: "업무가 없습니다",
			zeroRecords: "업무가 없습니다",
			info: "검색된 업무: _TOTAL_ 개",
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
			// 업무 등록
			{
				extends: 'create',
				text: '<i class="fas fa-pencil-alt fa-sm text-white-50"></i> 업무 등록',
				className: 'btn btn-sm btn-primary shadow-sm',
				action: function() {
					mainModal();
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
			// 선택한 업무 삭제 (DELETE) 
			{
				extends: 'delete',
				text: '<i class="fas fa-trash fa-sm text-white-50"></i> 선택 삭제',
				className: 'btn btn-sm btn-danger shadow-sm',
				enabled: false,
				action: function() {
					deletetask();				
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
	taskTable.buttons().container().appendTo( '.d-none d-inline-block btn btn-sm btn-primary shadow-sm' );
	
	// 특정 버튼을 매핑하는 기능 (트리거)(추후 기능 추가때 참조)
	$('.downloadLaw').click(function() {
		taskTable.button( '.buttons-excel' ).trigger();
	});
	
	// 선택된 row가 있으면 특정 버튼을 활성화
	taskTable.on( 'select deselect', function() {
		let selectedRows = taskTable.rows( { selected: true } ).count();
		taskTable.button(1).enable( selectedRows > 0 );	// 엑셀 버튼 활성화
		taskTable.button(2).enable( selectedRows > 0 );	// 삭제 버튼 활성화
	});
	
	/**************** 업무 CRUD ****************/
	// 업무 등록/수정 (Create/Update)
	$("#submit").click(function() {
		// 업무 정보 등록/수정 변수 담기
		if (type == 'POST') {	// 업무 등록 (POST)
			var taskData = JSON.stringify({
				parentId: $("#parentId").val(),
				name: $("#name").val(),
				icon: $("#icon").val(),
			});
		} else if (type == 'PUT') {		// 업무 수정 (PUT)
			var taskData = JSON.stringify({
				id: $("#id").val(),
				parentId: $("#parentId").val(),
				name: $("#name").val(),
				icon: $("#icon").val(),
			});
		}
		
		// 업무 등록/수정 처리
		$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/task',			
			type: type,
			data: taskData,
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 수정 성공
							/*alert('등록번호['+result.id+'] 업무 정보를 정상적으로 변경하였습니다.');*/
							alert('업무 정보를 정상적으로 변경하였습니다.');
							location.reload();
							break;
						case 201:	// 등록 성공
							alert('업무 정보를 정상적으로 등록하였습니다.');
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
	
	// 업무 삭제 (Delete)	
	function deletetask() {
		// 삭제할 업무의 ID를 param에 담기
		var param = taskTable.columns().checkboxes.selected()[0];
		
		if (confirm("업무[ "+param+" ]을 삭제하시겠습니까?")) {
			$.ajax ({
				contentType: 'application/json',
				url: '/api/task/'+param,
				type: 'DELETE',
				success: function(result) {
					switch(result.status) {
						case 200:	// 삭제 성공
							alert('등록번호['+param+'] 업무를 정상적으로 삭제하였습니다.');
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