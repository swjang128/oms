function profileOptional() {
	$('#profile-tab').removeClass('active');
	$('#profile-tab').attr('aria-selected', false);
	$('#billing-address-tab').addClass('active');
	
	$('#billing-address-tab').attr('aria-selected', true);
	$('#change-password-tab').removeClass('active');
	$('#notifications-tab').removeClass('active');
}