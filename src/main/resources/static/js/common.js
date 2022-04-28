// 디자인
window.hs_config = { "autopath": "@@autopath", "deleteLine": "hs-builder:delete", "deleteLine:build": "hs-builder:build-delete", "deleteLine:dist": "hs-builder:dist-delete", "previewMode": false, "startPath": "/index.html", "vars": { "themeFont": "https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap", "version": "?v=1.0" }, "layoutBuilder": { "extend": { "switcherSupport": true }, "header": { "layoutMode": "default", "containerMode": "container-fluid" }, "sidebarLayout": "default" }, "themeAppearance": { "layoutSkin": "default", "sidebarSkin": "default", "styles": { "colors": { "primary": "#377dff", "transparent": "transparent", "white": "#fff", "dark": "132144", "gray": { "100": "#f9fafc", "900": "#1e2022" } }, "font": "Inter" } }, "languageDirection": { "lang": "en" }, "skipFilesFromBundle": { "dist": ["assets/js/hs.theme-appearance.js", "assets/js/hs.theme-appearance-charts.js", "assets/js/demo.js"], "build": ["assets/css/theme.css", "assets/vendor/hs-navbar-vertical-aside/dist/hs-navbar-vertical-aside-mini-cache.js", "assets/js/demo.js", "assets/css/theme-dark.css", "assets/css/docs.css", "assets/vendor/icon-set/style.css", "assets/js/hs.theme-appearance.js", "assets/js/hs.theme-appearance-charts.js", "node_modules/chartjs-plugin-datalabels/dist/chartjs-plugin-datalabels.min.js", "assets/js/demo.js"] }, "minifyCSSFiles": ["assets/css/theme.css", "assets/css/theme-dark.css"], "copyDependencies": { "dist": { "*assets/js/theme-custom.js": "" }, "build": { "*assets/js/theme-custom.js": "", "node_modules/bootstrap-icons/font/*fonts/**": "assets/css" } }, "buildFolder": "", "replacePathsToCDN": {}, "directoryNames": { "src": "./src", "dist": "./dist", "build": "./build" }, "fileNames": { "dist": { "js": "theme.min.js", "css": "theme.min.css" }, "build": { "css": "theme.min.css", "js": "theme.min.js", "vendorCSS": "vendor.min.css", "vendorJS": "vendor.min.js" } }, "fileTypes": "jpg|png|svg|mp4|webm|ogv|json" }
window.hs_config.gulpRGBA = (p1) => {
	const options = p1.split(',')
	const hex = options[0].toString()
	const transparent = options[1].toString()

	var c;
	if (/^#([A-Fa-f0-9]{3}){1,2}$/.test(hex)) {
		c = hex.substring(1).split('');
		if (c.length == 3) {
			c = [c[0], c[0], c[1], c[1], c[2], c[2]];
		}
		c = '0x' + c.join('');
		return 'rgba(' + [(c >> 16) & 255, (c >> 8) & 255, c & 255].join(',') + ',' + transparent + ')';
	}
	throw new Error('Bad Hex');
}
window.hs_config.gulpDarken = (p1) => {
	const options = p1.split(',')

	let col = options[0].toString()
	let amt = -parseInt(options[1])
	var usePound = false

	if (col[0] == "#") {
		col = col.slice(1)
		usePound = true
	}
	var num = parseInt(col, 16)
	var r = (num >> 16) + amt
	if (r > 255) {
		r = 255
	} else if (r < 0) {
		r = 0
	}
	var b = ((num >> 8) & 0x00FF) + amt
	if (b > 255) {
		b = 255
	} else if (b < 0) {
		b = 0
	}
	var g = (num & 0x0000FF) + amt
	if (g > 255) {
		g = 255
	} else if (g < 0) {
		g = 0
	}
	return (usePound ? "#" : "") + (g | (b << 8) | (r << 16)).toString(16)
}
window.hs_config.gulpLighten = (p1) => {
	const options = p1.split(',')

	let col = options[0].toString()
	let amt = parseInt(options[1])
	var usePound = false

	if (col[0] == "#") {
		col = col.slice(1)
		usePound = true
	}
	var num = parseInt(col, 16)
	var r = (num >> 16) + amt
	if (r > 255) {
		r = 255
	} else if (r < 0) {
		r = 0
	}
	var b = ((num >> 8) & 0x00FF) + amt
	if (b > 255) {
		b = 255
	} else if (b < 0) {
		b = 0
	}
	var g = (num & 0x0000FF) + amt
	if (g > 255) {
		g = 255
	} else if (g < 0) {
		g = 0
	}
	return (usePound ? "#" : "") + (g | (b << 8) | (r << 16)).toString(16)
}

// Initialize
(function() {
	window.onload = function() {
		// Get LocationInformation
		var locationUrl = '/oms/'+$('#locationUrl').val();
		var locationName = $('#locationName').val();
		var parentLocationName = $('#parentLocationName').val();
		// Set BreadCrumb
		$('#breadcrumbLocationName').text(locationName);
		$('#breadcrumbParentLocationName').text(parentLocationName);
		// Set Sidebar Activation		
		$('#navbarVerticalMenu').find('.nav-link-title').each(function() {
			alert($(this).text() + ' | ' + locationName);
			if (locationName == $(this).text()) {
				alert($(this).text());
				$(this).parent().addClass('active');
			}
		});
		
		// Header의 사용자 세션정보의 상태(sessionUserStatus)에 따라 색상 변경
		const userStatus = $('#sessionUserStatus').text();
		switch (userStatus) {
			case '온라인':
				$('#sessionUserStatus').addClass('bg-primary');
				break;
			case '오프라인':
				$('#sessionUserStatus').addClass('bg-danger');
				break;
			case '자리비움':
				$('#sessionUserStatus').addClass('bg-warning');
				break;
			case '다른용무중':
				$('#sessionUserStatus').addClass('bg-info');
				break;
			default:
				$('#sessionUserStatus').addClass('bg-dark');
		}

		// Ajax 로딩
		$(document).ajaxStart(function() {
			$('.loading').removeClass('d-none');
		});
		$(document).ajaxStop(function() {
			$('.loading').addClass('d-none');
		});

		// INITIALIZATION OF NAVBAR VERTICAL ASIDE
		// =======================================================
		new HSSideNav('.js-navbar-vertical-aside').init();


		// INITIALIZATION OF FORM SEARCH
		// =======================================================
		new HSFormSearch('.js-form-search');


		// INITIALIZATION OF TOGGLE PASSWORD
		// =======================================================
		new HSTogglePassword('.js-toggle-password');


		// INITIALIZATION OF BOOTSTRAP DROPDOWN
		// =======================================================
		HSBsDropdown.init();


		// INITIALIZATION OF INPUT MASK
		// =======================================================
		HSCore.components.HSMask.init('.js-input-mask');


		// INITIALIZATION OF NAV SCROLLER
		// =======================================================
		new HsNavScroller('.js-nav-scroller');


		// INITIALIZATION OF COUNTER
		// =======================================================
		new HSCounter('.js-counter');


		// INITIALIZATION OF FILE ATTACHMENT
		// =======================================================
		new HSFileAttach('.js-file-attach');
		
		// INITIALIZATION OF SELECT
        // =======================================================
        HSCore.components.HSTomSelect.init('.js-select');


        // INITIALIZATION OF CLIPBOARD
        // =======================================================
        HSCore.components.HSClipboard.init('.js-clipboard');
	}
})();

// Theme
(function() {
	// STYLE SWITCHER
	// =======================================================
	const $dropdownBtn = document.getElementById('selectThemeDropdown') // Dropdown trigger
	const $variants = document.querySelectorAll(`[aria-labelledby="selectThemeDropdown"] [data-icon]`) // All items of the dropdown

	// Function to set active style in the dorpdown menu and set icon for dropdown trigger
	const setActiveStyle = function() {
		$variants.forEach($item => {
			if ($item.getAttribute('data-value') === HSThemeAppearance.getOriginalAppearance()) {
				$dropdownBtn.innerHTML = `<i class="${$item.getAttribute('data-icon')}" />`
				return $item.classList.add('active')
			}

			$item.classList.remove('active')
		})
	}

	// Add a click event to all items of the dropdown to set the style
	$variants.forEach(function($item) {
		$item.addEventListener('click', function() {
			HSThemeAppearance.setAppearance($item.getAttribute('data-value'))
		})
	})

	// Call the setActiveStyle on load page
	setActiveStyle()

	// Add event listener on change style to call the setActiveStyle function
	window.addEventListener('on-hs-appearance-change', function() {
		setActiveStyle()
	})
})();