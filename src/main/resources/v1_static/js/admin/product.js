/**************** 변수 선언부 ****************/
var url = '';
var id = 0;

/**************** 모달 제어부 ****************/
// 상품 등록 모달
var createModal = function createModal() {
	type = 'POST';	
	// 상품 상세/수정을 한 다음에는 각 항목 초기화	
	if ($('#modal-title').text() !== '상품 등록') {
		$('#name').val('');
		$('#type').val('HW');
		$('#category').val('PC');
		$('#price').val('');
		$('#cost').val('');
		$('#rated').val(0);
		$('#stock').val('');
		$('#registDate').val('');
		$('#discountRate').val('');
	}
	$("#modal-title").text("상품 등록");
	// 모달창 Open	
	$("#productModal").modal();
};

// 상품 상세/수정 모달
var updateModal = $(".updateModal:button").click(function() {
	type = 'PUT';
	
	// 상품의 상세 정보를 변수에 담기 (정규식 삭제)
	var row = $(this).parent().parent().children();
	var id = row.eq(1).text();
	var productType = row.eq(2).text();
	$('#type').val(productType).prop('selected', true);	
	var category = row.eq(3).text();
	$('#category').val(category).prop('selected', true);
	var name = row.eq(4).text();
	var price = row.eq(5).text();	
	price = price.replace(/,/gi, '');
	price = price.replace('원', '');	
	var cost = row.eq(6).text();
	cost = cost.replace(/,/gi, '');
	cost = cost.replace('원', '');
	var rated = row.eq(7).text();	
	rated = rated.replace('세', '');
	var stock = row.eq(8).text();
	stock = stock.replaceAll(/,/gi, '');
	stock = stock.replace('개', '');
	var releaseDate = row.eq(10).text();	
	var discountRate = row.eq(11).text();
	discountRate = discountRate.replace('%', '');

	$("#modal-title").text("상품 수정");
	$("#submitProduct").text("수정");
	
	// 모달창에 상품의 상세정보 입력
	$('#id').val(id);
	$("#name").val(name);
	$("#price").val(price);
	$("#cost").val(cost);
	$("#rated").val(rated);
	$("#stock").val(stock);
	$("#releaseDate").val(releaseDate);
	$("#discountRate").val(discountRate);
	
	// 모달창 Open
	$("#productModal").modal();
});

/**************** document.ready *****************/
$(document).ready(function() {
	
/**************** DataTable 설정 ****************/
  	var productTable = $('#productTable').DataTable({
		// 반응형웹 여부
		responsive: true,
		// 정규식 설정 (컬럼별)
		columnDefs: [
			{ targets: 0, data: 1, checkboxes: { selectRow: true }},	// 체크박스로 선택한 column[1]의 값을 데이터로 담음
			{ targets: 5, render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원') },
			{ targets: 6, render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원') },
			{ targets: 7, render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '세') },
			{ targets: 8, 
			  createdCell: function(td, cellData, rowData, rowIndex, colIndex) {	// 재고 개수에 따라 색상 다르게 표시
					if (cellData < 20) {	// 재고가 20개 미만인 경우 바탕색을 노란색
						$(td).css('background-color', 'yellow');
						$(td).css('color', 'blue');
						$(td).css('font-weight', 'bold');
					} 
					if (cellData == 0) {	// 재고가 0개인 경우 바탕색을 빨간색
						$(td).css('background-color', 'red');
						$(td).css('color', 'yellow');
						$(td).css('font-weight', 'bold');
					}
				}, 
			  render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '개') },
			{ targets: 11, render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '%') },
			{ targets: 12, render: $.fn.dataTable.render.number( ',' , '.' , 0 , '' , '원') },
			{ targets: 13, orderable: false, selectable: false }			
		],
		// 용어 설정
		language: {
			emptyTable: "상품이 없습니다",
			zeroRecords: "상품이 없습니다",
			info: "검색된 상품: _TOTAL_ 개",
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
			caseInsensitive: false
		},
		// 기능들의 위치 설정
		/*dom: 'fBlrtip',*/
		dom: "<'row'<'col-sm-12 col-md-6' l B><'col-sm-12 col-md-6'f>>" +
        "<'row'<'col-sm-12' rt>>" +
        "<'row'<'col-sm-12 col-md-5' i><'col-sm-12 col-md-7' p>>",
		// 버튼
		buttons: [
			// 상품 등록
			{
				extends: 'create',
				text: '<i class="fas fa-pencil-alt fa-sm text-white-50"></i> 상품 등록',
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
			// 선택한 상품 삭제 (DELETE) 
			{
				extends: 'delete',
				text: '<i class="fas fa-trash fa-sm text-white-50"></i> 선택 삭제',
				className: 'btn btn-sm btn-danger shadow-sm',
				enabled: false,
				action: function() {
					deleteProduct();
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
	productTable.buttons().container().appendTo( '.d-none d-inline-block btn btn-sm btn-primary shadow-sm' );
	
	// 특정 버튼을 매핑하는 기능 (트리거)(추후 기능 추가때 참조)
	$('.downloadProduct').click(function() {
		productTable.button( '.buttons-excel' ).trigger();
	});
	
	// 선택된 row가 있으면 특정 버튼을 활성화
	productTable.on( 'select deselect', function() {
		let selectedRows = productTable.rows( { selected: true } ).count();
		productTable.button(1).enable( selectedRows > 0 );	// 엑셀 버튼 활성화
		productTable.button(2).enable( selectedRows > 0 );	// 삭제 버튼 활성화
	});
	
	/**************** 상품 CRUD ****************/
	// 상품 등록/수정 (Create/Update)
	$("#submitProduct").click(function() {
		// 각 항목에 대한 데이터 검증
		if (!$("#name").val()) {
			alert("상품명을 입력하세요!");
			$("#name").focus();
			return;
		}
		if ($("#price").val() < 0) {
			alert("판매가는 0보다 작을 수 없습니다.");
			$("#price").focus();
			return;
		}
		if ($("cost").val() < 0) {
			alert("매입가는 0보다 작을 수 없습니다.");
			$("#cost").focus();
			return;
		}
		if ($("#stock").val() < 0) {
			alert("재고는 0보다 작을 수 없습니다.");
			$("#stock").focus();
			return;
		}
		if (!$("#releaseDate").val()) {
			alert("발매일를 입력하세요!");
			$("#releaseDate").focus();
			return;
		}
		if ($("#discountRate").val() > 100 ||
			$("#discountRate").val() < 0) {
			alert("할인율은 0~100%까지만 입력할 수 있습니다.");
			$("#discountRate").focus();
			return;
		}
		
		// 상품 정보 등록/수정
		if (type == 'POST') {	// 상품 등록 (POST)
			var productData = JSON.stringify({
				name: $("#name").val(),
				type: $("#type").val(),
				category: $("#category").val(),
				price: $("#price").val(),
				cost: $("#cost").val(),
				rated: $("#rated").val(),
				stock: $("#stock").val(),
				releaseDate: $("#releaseDate").val(),
				discountRate: $("#discountRate").val()
			});
		} else if (type == 'PUT') {
			var productData = JSON.stringify({
				id: $("#id").val(),
				name: $("#name").val(),
				type: $("#type").val(),
				category: $("#category").val(),
				price: $("#price").val(),
				cost: $("#cost").val(),
				rated: $("#rated").val(),
				stock: $("#stock").val(),
				releaseDate: $("#releaseDate").val(),
				discountRate: $("#discountRate").val()
			});
		}
		
		// 상품 등록/수정 처리
		$.ajax({
			contentType: "application/json; charset=utf-8",
			url: '/api/product',			
			type: type,
			data: productData,
			cache: false,
			success: function(result) {
				switch(result.status) {
						case 200:	// 수정 성공
							/*alert('등록번호['+result.id+'] 상품 정보를 정상적으로 변경하였습니다.');*/
							alert('상품 정보를 정상적으로 변경하였습니다.');
							location.reload();
							break;
						case 201:	// 등록 성공
							alert('상품 정보를 정상적으로 등록하였습니다.');
							location.reload();
							break;
/*						case 401:	// 파라미터 오류
							alert('잘못된 파라미터입니다.');
							
							break;*/
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
	
	// 상품 삭제 (Delete)	
	function deleteProduct() {
		// 삭제할 상품의 ID를 param에 담기
		var param = productTable.columns().checkboxes.selected()[0];
		
		if (confirm("상품[ "+param+" ]을 삭제하시겠습니까?")) {
			$.ajax ({
				contentType: 'application/json',
				url: '/api/product/'+param,
				type: 'DELETE',
				success: function(result) {
					switch(result.status) {
						case 200:	// 삭제 성공
							alert('등록번호['+param+'] 상품을 정상적으로 삭제하였습니다.');
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