/*var inputValue = false;

$(function(){
// 옵션을 설정한다.
dialog.set({
	// 다이얼로그가 show될 때의 액션 설정
	show: {
		effect: "fade",
		duration: 100
	},
	// 다이얼로그가 hide될 때의 액션 설정
	hide: {
		effect: "fade",
		duration : 100
	},
	// 타이틀 설정
	title : "Dialog Test",
	// 모달 여부
	modal : true,
	// 버튼 설정
	buttons : {
		// OK
		OK : function() {
			if (!confirm('등록하시겠습니까?')) {
				console.log('false');
				inputValue = false;
				$('#result').html("False");
			} else {
				console.log('true');
				inputValue = true;
				$('#result').html("True");
			}
			dialog.close();
		},
		// Cancel
		Cancel : function() {
			$('#result').html("Canceled");
			dialog.close();
		},
		// Modal
		Modal : function() {
			$('#result').html("Modal");
			dialog.close();
		}
	}
});
	$(".btn-primary").on("click", function() {
		// 다이얼로그를 연다.
		dialog.open("<p>Dialog Content</p>");
	});
});*/