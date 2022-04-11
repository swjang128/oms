/************************
* rememberMe Check *
*************************/
function rememberMeCheck() {
	var rememberMe = $('#rememberMe').is(':checked');
	if (rememberMe == false) {
		$('#rememberMeMessage').removeClass('text-dark');
		$('#rememberMeMessage').addClass('text-muted');
	} else {
		$('#rememberMeMessage').removeClass('text-muted');
		$('#rememberMeMessage').addClass('text-dark');
	}
}
