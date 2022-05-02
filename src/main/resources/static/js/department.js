$(document).on('ready', function() {
	// INITIALIZATION OF DATATABLES
	// =======================================================
	HSCore.components.HSDatatables.init($('#datatable'), {
		select: {
			style: 'multi',
			selector: 'td:first-child input[type="checkbox"]',
			classMap: {
				checkAll: '#datatableCheckAll',
				counter: '#datatableCounter',
				counterInfo: '#datatableCounterInfo'
			}
		},
		language: {
			zeroRecords: `<div class="text-center p-4">
              <img class="mb-3" src="/svg/illustrations/oc-error.svg" alt="Image Description" style="width: 10rem;" data-hs-theme-appearance="default">
              <img class="mb-3" src="/svg/illustrations-light/oc-error.svg" alt="Image Description" style="width: 10rem;" data-hs-theme-appearance="dark">
            <p class="mb-0">No data to show</p>
            </div>`
		}
	});

	const datatable = HSCore.components.HSDatatables.getItem('datatable')

	$('.js-datatable-filter').on('change', function() {
		var $this = $(this),
			elVal = $this.val(),
			targetColumnIndex = $this.data('target-column-index');

		datatable.column(targetColumnIndex).search(elVal).draw();
	});

	$('#datatableSearch').on('mouseup', function(e) {
		var $input = $(this),
			oldValue = $input.val();

		if (oldValue == "") return;

		setTimeout(function() {
			var newValue = $input.val();

			if (newValue == "") {
				// Gotcha
				datatable.search('').draw();
			}
		}, 1);
	});

	$('#toggleColumn_product').change(function(e) {
		datatable.columns(1).visible(e.target.checked)
	})

	$('#toggleColumn_type').change(function(e) {
		datatable.columns(2).visible(e.target.checked)
	})

	datatable.columns(3).visible(false)

	$('#toggleColumn_vendor').change(function(e) {
		datatable.columns(3).visible(e.target.checked)
	})

	$('#toggleColumn_stocks').change(function(e) {
		datatable.columns(4).visible(e.target.checked)
	})

	$('#toggleColumn_sku').change(function(e) {
		datatable.columns(5).visible(e.target.checked)
	})

	$('#toggleColumn_price').change(function(e) {
		datatable.columns(6).visible(e.target.checked)
	})

	datatable.columns(7).visible(false)

	$('#toggleColumn_quantity').change(function(e) {
		datatable.columns(7).visible(e.target.checked)
	})

	$('#toggleColumn_variants').change(function(e) {
		datatable.columns(8).visible(e.target.checked)
	})
});