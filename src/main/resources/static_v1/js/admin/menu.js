/**************** 모달 제어부 ****************/
// 메인 카테고리를 선택하면 부모 카테고리들을 가져옴
$('#mainCategory').on('change', function() {
	mainCategoryId = $('#mainCategory').val();
	
	// parentCategory 셀렉트박스의 하위 항목들을 모두 삭제
	$('#parentCategory').empty();
	
	// 카테고리 소분류 리스트 조회
	$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/category/'+mainCategoryId,
			type: 'GET',			
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 조회 성공
							for (var c=0; c<result.categoryList.length; c++) {
								$('#parentCategory').append('<option value='+result.categoryList[c].id+'>'+
															result.categoryList[c].name+
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

// 소분류를 선택하면 카테고리 목록을 가져옴
$('#parentCategory').on('change', function() {
	console.log('parent');
	$('#test').text($('#parentCategory').val());
	type = 'GET';
});


/**************************************************************************
* 등록할 카테고리를 선택하는 모달(이 모달을 열면 메인 카테고리도 조회한다)*
***************************************************************************/
function createModal() {
	$('#menuModal').modal();
	
};

/****************
* 메인 카테고리 모달 *
*****************/ 
function mainModal() {
	$('#mainModal').modal();
	$('#mainCategory').empty();
	// 메인 카테고리 목록 조회
	$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/category/'+0,			
			type: 'GET',			
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 조회 성공
							for (var c=0; c<result.categoryList.length; c++) {
								$('#mainCategory').append('<option value='+result.categoryList[c].id+'>'+
															result.categoryList[c].name+
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
}


















// 카테고리 상세/수정 모달
var updateModal = $(".updateModal:button").click(function() {
	type = 'PUT';
	
	// 카테고리의 상세 정보를 변수에 담기 (정규식 삭제)
	var row = $(this).parent().parent().children();
	var id = row.eq(0).text();
	var parentId = row.eq(1).text();
	var name = row.eq(2).text();
	var icon = row.eq(3).text();

	$("#modal-title").text("카테고리 수정");
	$("#submitLaw").text("수정");
	
	// 모달창에 카테고리의 상세정보 입력
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
  	var categoryTable = $('#table').DataTable({
		// 반응형웹 여부
		responsive: true,
		// 정규식 설정 (컬럼별)
		columnDefs: [
			{ targets: 0, data: 0, checkboxes: { selectRow: true } }	// 체크박스로 선택한 column[0]의 값을 데이터로 담음
		],
		// 용어 설정
		language: {
			emptyTable: "카테고리가 없습니다",
			zeroRecords: "카테고리가 없습니다",
			info: "검색된 카테고리: _TOTAL_ 개",
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
			// 카테고리 등록
			{
				extends: 'create',
				text: '<i class="fas fa-pencil-alt fa-sm text-white-50"></i> 메뉴 관리',
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
			// 선택한 카테고리 삭제 (DELETE) 
			{
				extends: 'delete',
				text: '<i class="fas fa-trash fa-sm text-white-50"></i> 선택 삭제',
				className: 'btn btn-sm btn-danger shadow-sm',
				enabled: false,
				action: function() {
					deleteCategory();				
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
	categoryTable.buttons().container().appendTo( '.d-none d-inline-block btn btn-sm btn-primary shadow-sm' );
	
	// 특정 버튼을 매핑하는 기능 (트리거)(추후 기능 추가때 참조)
	$('.downloadLaw').click(function() {
		categoryTable.button( '.buttons-excel' ).trigger();
	});
	
	// 선택된 row가 있으면 특정 버튼을 활성화
	categoryTable.on( 'select deselect', function() {
		let selectedRows = categoryTable.rows( { selected: true } ).count();
		categoryTable.button(1).enable( selectedRows > 0 );	// 엑셀 버튼 활성화
		categoryTable.button(2).enable( selectedRows > 0 );	// 삭제 버튼 활성화
	});
	
	/**************** 카테고리 CRUD ****************/
	// 카테고리 등록/수정 (Create/Update)
	$("#submit").click(function() {
		// 카테고리 정보 등록/수정 변수 담기
		if (type == 'POST') {	// 카테고리 등록 (POST)
			var categoryData = JSON.stringify({
				parentId: $("#parentId").val(),
				name: $("#name").val(),
				icon: $("#icon").val(),
			});
		} else if (type == 'PUT') {		// 카테고리 수정 (PUT)
			var categoryData = JSON.stringify({
				id: $("#id").val(),
				parentId: $("#parentId").val(),
				name: $("#name").val(),
				icon: $("#icon").val(),
			});
		}
		
		// 카테고리 등록/수정 처리
		$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/category',			
			type: type,
			data: categoryData,
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 수정 성공
							/*alert('등록번호['+result.id+'] 카테고리 정보를 정상적으로 변경하였습니다.');*/
							alert('카테고리 정보를 정상적으로 변경하였습니다.');
							location.reload();
							break;
						case 201:	// 등록 성공
							alert('카테고리 정보를 정상적으로 등록하였습니다.');
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
	
	// 카테고리 삭제 (Delete)	
	function deleteCategory() {
		// 삭제할 카테고리의 ID를 param에 담기
		var param = categoryTable.columns().checkboxes.selected()[0];
		
		if (confirm("카테고리[ "+param+" ]을 삭제하시겠습니까?")) {
			$.ajax ({
				contentType: 'application/json',
				url: '/api/category/'+param,
				type: 'DELETE',
				success: function(result) {
					switch(result.status) {
						case 200:	// 삭제 성공
							alert('등록번호['+param+'] 카테고리을 정상적으로 삭제하였습니다.');
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