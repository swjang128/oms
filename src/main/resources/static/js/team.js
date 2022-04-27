// document on Ready
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

	const datatable = HSCore.components.HSDatatables.getItem(0)

	$('.js-datatable-filter').on('change', function() {
		var $this = $(this),
			elVal = $this.val(),
			targetColumnIndex = $this.data('target-column-index');

		if (elVal === 'null') elVal = ''

		datatable.column(targetColumnIndex).search(elVal).draw();
	});
});

