/**************** 변수 선언부 ****************/
var url = '';
var id = 0;

/**************** 모달 제어부 ****************/
// 법안 등록 모달
var createModal = function createModal() {
	type = 'POST';	
	// 법안 상세/수정을 한 다음에는 각 항목 초기화	
	if ($('#modal-title').text() !== '법안 등록') {
		$('#first').val('');
		$('#second').val('');
		$('#third').val('');
		$('#fourth').val('');
		$('#fifth').val('');
		$('#sixth').val('');
		$('#wildCard').val('');
	}
	$("#modal-title").text("법안 등록");
	// 모달창 Open	
	$("#lawModal").modal();
};

// 법안 상세/수정 모달
var updateModal = $(".updateModal:button").click(function() {
	type = 'PUT';
	
	// 법안의 상세 정보를 변수에 담기 (정규식 삭제)
	var row = $(this).parent().parent().children();
	var id = row.eq(1).text();
	var first = row.eq(2).text();
	var second = row.eq(3).text();
	var third = row.eq(4).text();
	var fourth = row.eq(5).text();
	var fifth = row.eq(6).text();
	var sixth = row.eq(7).text();
	var wildCard = row.eq(8).text();
	

	$("#modal-title").text("법안 수정");
	$("#submitLaw").text("수정");
	
	// 모달창에 법안의 상세정보 입력
	$('#id').val(id);
	$("#first").val(first);
	$("#second").val(second);
	$("#third").val(third);
	$("#fourth").val(fourth);
	$("#fifth").val(fifth);
	$("#sixth").val(sixth);
	$("#wildCard").val(wildCard);
	
	// 모달창 Open
	$("#lawModal").modal();
});

/**************** document.ready *****************/
$(document).ready(function() {
	
/**************** DataTable 설정 ****************/
  	var lawTable = $('#lawTable').DataTable({
		// 반응형웹 여부
		responsive: true,
		// 정규식 설정 (컬럼별)
		columnDefs: [
			{ targets: 0, data: 1, checkboxes: { selectRow: true } }	// 체크박스로 선택한 column[1]의 값을 데이터로 담음
		],
		// 용어 설정
		language: {
			emptyTable: "법안이 없습니다",
			zeroRecords: "법안이 없습니다",
			info: "검색된 법안: _TOTAL_ 개",
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
			// 법안 등록
			{
				extends: 'create',
				text: '<i class="fas fa-pencil-alt fa-sm text-white-50"></i> 법안 등록',
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
			// 선택한 법안 삭제 (DELETE) 
			{
				extends: 'delete',
				text: '<i class="fas fa-trash fa-sm text-white-50"></i> 선택 삭제',
				className: 'btn btn-sm btn-danger shadow-sm',
				enabled: false,
				action: function() {
					deleteLaw();				
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
	lawTable.buttons().container().appendTo( '.d-none d-inline-block btn btn-sm btn-primary shadow-sm' );
	
	// 특정 버튼을 매핑하는 기능 (트리거)(추후 기능 추가때 참조)
	$('.downloadLaw').click(function() {
		lawTable.button( '.buttons-excel' ).trigger();
	});
	
	// 선택된 row가 있으면 특정 버튼을 활성화
	lawTable.on( 'select deselect', function() {
		let selectedRows = lawTable.rows( { selected: true } ).count();
		lawTable.button(1).enable( selectedRows > 0 );	// 엑셀 버튼 활성화
		lawTable.button(2).enable( selectedRows > 0 );	// 삭제 버튼 활성화
	});
	
	/**************** 법안 CRUD ****************/
	// 법안 등록/수정 (Create/Update)
	$("#submitLaw").click(function() {
		// 각 항목에 대한 데이터 검증
		if ($("#first").val() > 45 || $("#first").val() < 1) {			
			alert("First는 1~45까지만 입력할 수 있습니다.");
			$("#first").focus();
			return;
		}
		if ($("#second").val() > 45 || $("#second").val() < 1) {			
			alert("Second는 1~45까지만 입력할 수 있습니다.");
			$("#second").focus();
			return;
		}
		if ($("#third").val() > 45 || $("#third").val() < 1) {			
			alert("Third는 1~45까지만 입력할 수 있습니다.");
			$("#third").focus();
			return;
		}
		if ($("#fourth").val() > 45 || $("#fourth").val() < 1) {			
			alert("Fourth는 1~45까지만 입력할 수 있습니다.");
			$("#fourth").focus();
			return;
		}
		if ($("#fifth").val() > 45 || $("#fifth").val() < 1) {			
			alert("Fifth는 1~45까지만 입력할 수 있습니다.");
			$("#fifth").focus();
			return;
		}
		if ($("#sixth").val() > 45 || $("#sixth").val() < 1) {			
			alert("Sixth는 1~45까지만 입력할 수 있습니다.");
			$("#sixth").focus();
			return;
		}
		if ($("#wildCard").val() > 45 || $("#wildCard").val() < 1) {			
			alert("WildCard는 1~45까지만 입력할 수 있습니다.");
			$("#wildCard").focus();
			return;
		}
		
		// 법안 정보 등록/수정 변수 담기
		if (type == 'POST') {	// 법안 등록 (POST)
			var lawData = JSON.stringify({
				first: $("#first").val(),
				second: $("#second").val(),
				third: $("#third").val(),
				fourth: $("#fourth").val(),
				fifth: $("#fifth").val(),
				sixth: $("#sixth").val(),
				wildCard: $("#wildCard").val()
			});
		} else if (type == 'PUT') {		// 법안 수정 (PUT)
			var lawData = JSON.stringify({
				id: $("#id").val(),
				first: $("#first").val(),
				second: $("#second").val(),
				third: $("#third").val(),
				fourth: $("#fourth").val(),
				fifth: $("#fifth").val(),
				sixth: $("#sixth").val(),
				wildCard: $("#wildCard").val()
			});
		}
		
		// 법안 등록/수정 처리
		$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/law',			
			type: type,
			data: lawData,
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 수정 성공
							/*alert('등록번호['+result.id+'] 법안 정보를 정상적으로 변경하였습니다.');*/
							alert('법안 정보를 정상적으로 변경하였습니다.');
							location.reload();
							break;
						case 201:	// 등록 성공
							alert('법안 정보를 정상적으로 등록하였습니다.');
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
	
	// 법안 삭제 (Delete)	
	function deleteLaw() {
		// 삭제할 법안의 ID를 param에 담기
		var param = lawTable.columns().checkboxes.selected()[0];
		
		if (confirm("법안[ "+param+" ]을 삭제하시겠습니까?")) {
			$.ajax ({
				contentType: 'application/json',
				url: '/api/law/'+param,
				type: 'DELETE',
				success: function(result) {
					switch(result.status) {
						case 200:	// 삭제 성공
							alert('등록번호['+param+'] 법안을 정상적으로 삭제하였습니다.');
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