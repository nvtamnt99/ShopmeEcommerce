var buttonLoad;
var dropDownCountry;
var buttonAddCountry;
var buttonUpdateCountry;
var buttonDeleteCountry;
var labelCountryName;
var fieldCountryName;
var fieldCountryCode;

$(document).ready(function() {
	buttonLoad = $("#buttonLoadCountries");
	dropDownCountry = $("#dropDownCountries");
	buttonAddCountry = $("#buttonAddCountry");
	buttonUpdateCountry = $("#buttonUpdateCountry");
	buttonDeleteCountry = $("#buttonDeleteCountry");
	labelCountryName = $("#labelCountryName");
	fieldCountryName = $("#fieldCountryName");
	fieldCountryCode = $("#fieldCountryCode");
	
	buttonLoad.click(function() {
		loadCountries();
	});
	
	dropDownCountry.on("change", function() {
		changeFormStateToSelectedCountry();
	});
	
	buttonAddCountry.click(function() {
		if (buttonAddCountry.val() == "Thêm") {
			addCountry();
		} else {
			changeFormStateToNewCountry();
		}
	});
	
	buttonUpdateCountry.click(function() {
		updateCountry();
	});
	
	buttonDeleteCountry.click(function() {
		deleteCountry();
	});
});

function deleteCountry() {
	optionValue = dropDownCountry.val();
	countryId = optionValue.split("-")[0];
	
	url = contextPath + "datnuocs/xoa/" + countryId;
	
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownCountries option[value='" + optionValue + "']").remove();
		changeFormStateToNewCountry();
		showToastMessage("Quốc gia đã bị xóa");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});		
}

function updateCountry() {
	if (!validateFormCountry()) return;
	
	url = contextPath + "datnuocs/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	
	countryId = dropDownCountry.val().split("-")[0];
	
	jsonData = {maDatnuoc: countryId, ten: countryName, maVung: countryCode};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countryId) {
		$("#dropDownCountries option:selected").val(countryId + "-" + countryCode);
		$("#dropDownCountries option:selected").text(countryName);
		showToastMessage("Quốc gia đã được cập nhật");
		
		changeFormStateToNewCountry();
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});	
}

function validateFormCountry() {
	formCountry = document.getElementById("formCountry");
	if (!formCountry.checkValidity()) {
		formCountry.reportValidity();
		return false;
	}	
	
	return true;
}

function addCountry() {

	if (!validateFormCountry()) return;
	
	url = contextPath + "datnuocs/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	jsonData = {ten: countryName, maVung: countryCode};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countryId) {
		selectNewlyAddedCountry(countryId, countryCode, countryName);
		showToastMessage("Quốc gia mới đã được thêm vào");
	}).fail(function() {
		showToastMessage("ERROR: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});
		
}

function selectNewlyAddedCountry(countryId, countryCode, countryName) {
	optionValue = countryId + "-" + countryCode;
	$("<option>").val(optionValue).text(countryName).appendTo(dropDownCountry);
	
	$("#dropDownCountries option[value='" + optionValue + "']").prop("selected", true);
	
	fieldCountryCode.val("");
	fieldCountryName.val("").focus();
}

function changeFormStateToNewCountry() {
	buttonAddCountry.val("Thêm");
	labelCountryName.text("Tên quốc gia:");
	
	buttonUpdateCountry.prop("disabled", true);
	buttonDeleteCountry.prop("disabled", true);
	
	fieldCountryCode.val("");
	fieldCountryName.val("").focus();	
}

function changeFormStateToSelectedCountry() {
	buttonAddCountry.prop("value", "Tạo mới");
	buttonUpdateCountry.prop("disabled", false);
	buttonDeleteCountry.prop("disabled", false);
	
	labelCountryName.text("Chọn quốc gia:");
	
	selectedCountryName = $("#dropDownCountries option:selected").text();
	fieldCountryName.val(selectedCountryName);
	
	countryCode = dropDownCountry.val().split("-")[1];
	fieldCountryCode.val(countryCode);
	
}

function loadCountries() {
	url = contextPath + "datnuocs/list";
	$.get(url, function(responseJSON) {
		dropDownCountry.empty();
		
		$.each(responseJSON, function(index, country) {
			optionValue = country.maDatnuoc + "-" + country.maVung;
			$("<option>").val(optionValue).text(country.ten).appendTo(dropDownCountry);
		});
		
	}).done(function() {
		buttonLoad.val("Làm mới danh sách quốc gia");
		showToastMessage("Tất cả các quốc gia đã được tải");
	}).fail(function() {
		showToastMessage("ERROR: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});
}

function showToastMessage(message) {
	$("#toastMessage").text(message);
	$(".toast").toast('show');
}