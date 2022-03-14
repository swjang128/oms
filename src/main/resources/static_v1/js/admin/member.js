/**************** 변수 선언부 ****************/
var id = 0;
var servletPath = $("#servletPath").val();

/**************** 모달 제어부 ****************/
// 직원 등록 모달
var createModal = function createModal() {
	type = 'POST';
	// 직원 상세/수정을 한 다음에는 각 항목 초기화	
	if ($('#modal-title').text() !== '직원 등록') {		
		$('#name').val('');
		$('#department').val('매장');
		$('#position').val('사원');
		$('#photo').val('');
		$('#email').val('');
		$('#phone').val(0);
		$('#emergencyContact').val('');
		$('#birthday').val('');
		let today = new Date();
		$('#hireDate').val(today);
	}
	$("#modal-title").text("직원 등록");
	// 모달창 Open	
	$("#memberModal").modal();
};

// 직원 상세/수정 모달
var updateModal = $(".updateModal:button").click(function() {
	type = 'PUT';
	// 직원의 상세 정보를 변수에 담기 (정규식 삭제)
	var row = $(this).parent().parent().children();
	var photo = row.eq(1).text();
	var id = row.eq(2).text();
	var department = row.eq(3).text();
	$('#department').val(department).prop('selected', true);
	var position = row.eq(4).text();
	$('#position').val(position).prop('selected', true);
	var name = row.eq(5).text();
	var email = row.eq(6).text();
	var phone = row.eq(7).text();	
	phone = phone.replaceAll(/-/gi, '');
	var emergencyContact = row.eq(8).text();
	emergencyContact = emergencyContact.replaceAll(/-/gi, '');	
	var birthday = row.eq(9).text();	
	var hireDate = row.eq(10).text();

	$("#modal-title").text("직원 정보 수정");
	$("#submitMember").text("수정");
	
	// 모달창에 직원의 상세정보 입력
	$('#id').val(id);
	$("#photo").val(photo);
	$("#department").val(department);
	$("#position").val(position);
	$("#name").val(name);
	$("#email").val(email);
	$("#phone").val(phone);
	$("#emergencyContact").val(emergencyContact);
	$("#birthday").val(birthday);
	$("#hireDate").val(hireDate);
	
	// 모달창 Open
	$("#memberModal").modal();
});

// 재고가 없는 Row는 background-color: red 로 set
/*var stock = $("#memberStock").text();
alert(stock);
*/

/**************** document.ready *****************/
$(document).ready(function() {
	
/**************** DataTable 설정 ****************/
  	var memberTable = $('#memberTable').DataTable({
		// 반응형웹 여부
		responsive: true,
		// 정규식 설정 (컬럼별)
		columnDefs: [
			{ targets: 0, data: 2, checkboxes: { selectRow: true } },		// 체크박스로 선택한 column[2]의 값을 데이터로 담음
			{ targets: 1, orderable: false, selectable: false },
			{ targets: 3, orderable: false },
			{ targets: 6, orderable: false },
			{ targets: 7, orderable: false },
			{ targets: 8, orderable: false },
			{ targets: 11, orderable: false, selectable: false }			
		],
		// 용어 설정
		language: {
			emptyTable: "직원이 없습니다",
			zeroRecords: "직원이 없습니다",
			info: "전체 _TOTAL_ 명",
			infoEmpty: "검색된 결과가 없습니다",
			infoFiltered: "",
			search: "검색",
			select: {
				rows: {
					_: "%d명 선택됨",
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
			caseInsensitive: false
		},
		// 기능들의 위치 설정
		/*dom: 'fBlrtip',*/
		dom: "<'row'<'col-sm-12 col-md-6' l B><'col-sm-12 col-md-6'f>>" +
        "<'row'<'col-sm-12' rt>>" +
        "<'row'<'col-sm-12 col-md-5' i><'col-sm-12 col-md-7' p>>",
		// 버튼
		buttons: [
			// 직원 등록
			{
				extends: 'create',
				text: '<i class="fas fa-pencil-alt fa-sm text-white-50"></i> 직원 등록',
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
			// 선택한 직원 정보 삭제 (DELETE) 
			{
				extends: 'delete',
				text: '<i class="fas fa-trash fa-sm text-white-50"></i> 선택 삭제',
				className: 'btn btn-sm btn-danger shadow-sm',
				enabled: false,
				action: function() {
					deleteMember();				
				}
			}
		],
		// row 선택 설정
		select: {
			style: 'multi+shift',
			selector: 'td:not(:last-child)'	// 해당 row의 마지막 td는 클릭해도 selected 상태가 되지 않는다.		
		},
		// 초기 정렬 설정
		order: [[2, 'asc']]
	});
	
	// 버튼 디자인
	memberTable.buttons().container().appendTo( '.d-none d-inline-block btn btn-sm btn-primary shadow-sm' );
	
	// 특정 버튼을 매핑하는 기능 (트리거)(추후 기능 추가때 참조)
	$('.downloadProduct').click(function() {
		memberTable.button( '.buttons-excel' ).trigger();
	});
	
	// 선택된 row가 있으면 특정 버튼을 활성화
	memberTable.on( 'select deselect', function() {
		let selectedRows = memberTable.rows( { selected: true } ).count();
		memberTable.button(1).enable( selectedRows > 0 );	// 엑셀 버튼 활성화
		memberTable.button(2).enable( selectedRows > 0 );	// 삭제 버튼 활성화
	});
	
	/**************** 직원 CRUD ****************/
	// 직원 등록/수정 (Create/Update)
	$("#submitMember").click(function() {
		// 초기 비밀번호는 1234로 설정		
		// 직원 정보 등록/수정		
		if (type == 'POST') {	// 직원 등록 (POST)
			var memberData = JSON.stringify({
				password: '1234',
				department: $("#department").val(),
				position: $("#position").val(),
				photo: $("#photo").val(),
				name: $("#name").val(),				
				email: $("#email").val(),
				phone: $("#phone").val(),
				emergencyContact: $("#emergencyContact").val(),
				birthday: $("#birthday").val(),
				hireDate: $("#hireDate").val()
			});
		} else if (type == 'PUT') {
			var memberData = JSON.stringify({
				id: $("#id").val(),
				department: $("#department").val(),
				position: $("#position").val(),
				photo: $("#photo").val(),
				name: $("#name").val(),
				email: $("#email").val(),
				phone: $("#phone").val(),
				emergencyContact: $("#emergencyContact").val(),
				birthday: $("#birthday").val(),
				hireDate: $("#hireDate").val()
			});
		}
		
		// 직원 등록/수정 처리
		$.ajax({
			url: '/api/member',
			contentType: "application/json; charset=utf-8",
			type: type,
			data: memberData,
			cache: false,
			success: function(result) {
				alert(result);
			},
			error: function() {
				alert('서버와의 통신에 실패했습니다.');
			},
			complete: function() {
				location.reload();
			}
		});
	});
	
	// 직원 삭제 (Delete)	
	function deleteMember() {
		// 삭제할 직원의 ID를 payload 변수에 담기
		var payload = memberTable.columns().checkboxes.selected()[0];
		
		if (confirm('직원[ '+payload+' ] 정보를 삭제하시겠습니까?')) {
			$.ajax ({
				contentType: 'application/json',
				url: '/api/member/'+payload,
				type: 'DELETE',
				//data: payload,
				success: function(result) {
					if (result.status == 200) {
						alert('정상적으로 삭제하였습니다.');
					} else {
						alert('직원[ '+payload+' ] 정보를 삭제하지 못했습니다.');							
					}
				},
				error: function() {
					alert('서버와의 통신에 실패하였습니다');
				},
				complete: function() {
					location.reload();
				}
			});
		}
	}

});