dropdownBrands = $("#nhanhieu");
dropdownCategories = $("#danhmuc");

$(document).ready(function() {

	$("#moTaNgan").richText();
	$("#moTaDayDu").richText();

	dropdownBrands.change(function() {
		dropdownCategories.empty();
		getCategories();
	});	

	getCategoriesForNewForm();

});

function getCategoriesForNewForm() {
	catIdField = $("#categoryId");
	editMode = false;

	if (catIdField.length) {
		editMode = true;
	}

	if (!editMode) getCategories();
}

function getCategories() {
	brandId = dropdownBrands.val(); 
	url = brandModuleURL + "/" + brandId + "/danhmuc";

	$.get(url, function(responseJson) {
		$.each(responseJson, function(index, category) {
			$("<option>").val(category.maDanhMuc).text(category.ten).appendTo(dropdownCategories);
		});			
	});
}

function checkUnique(form) {
	productId = $("#maSanPham").val();
	productName = $("#ten").val();

	csrfValue = $("input[name='_csrf']").val();

	params = {maSanPham: productId, ten: productName, _csrf: csrfValue};

	$.post(checkUniqueUrl, params, function(response) {
		if (response == "OK") {
			form.submit();
		} else if (response == "Duplicate") {
			showWarningModal("Có một sản phẩm khác có tên " + productName);	
		} else {
			showErrorModal("Unknown response from server");
		}

	}).fail(function() {
		showErrorModal("Could not connect to the server");
	});

	return false;
}