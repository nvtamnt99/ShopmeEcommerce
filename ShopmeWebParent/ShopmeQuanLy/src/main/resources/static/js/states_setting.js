var buttonLoad4States;
var dropDownCountry4States;
var dropDownStates;
var buttonAddState;
var buttonUpdateState;
var buttonDeleteState;
var labelStateName;
var fieldStateName;

$(document).ready(function() {
	buttonLoad4States = $("#buttonLoadCountriesForStates");
	dropDownCountry4States = $("#dropDownCountriesForStates");
	dropDownStates = $("#dropDownStates");
	buttonAddState = $("#buttonAddState");
	buttonUpdateState = $("#buttonUpdateState");
	buttonDeleteState = $("#buttonDeleteState");
	labelStateName = $("#labelStateName");
	fieldStateName = $("#fieldStateName");
	
	buttonLoad4States.click(function() {
		loadCountries4States();
	});
	
	dropDownCountry4States.on("change", function() {
		loadStates4Country();
	});

	dropDownStates.on("change", function() {
		changeFormStateToSelectedState();
	});
		
	buttonAddState.click(function() {
		if (buttonAddState.val() == "Thêm") {
			addState();
		} else {
			changeFormStateToNew();
		}
	});
	
	buttonUpdateState.click(function() {
		updateState();
	});
	
	buttonDeleteState.click(function() {
		deleteState();
	});
});

function deleteState() {
	stateId = dropDownStates.val().split();
	
	url = contextPath + "tinhs/xoa/" + stateId;
	
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownStates option[value='" + stateId + "']").remove();
		changeFormStateToNew();
		showToastMessage("Tỉnh đã bị xóa");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});		
}

function updateState() {
	if (!validateFormState()) return;
	
	url = contextPath + "tinhs/save";
	stateId = dropDownStates.val().split("")[1];
	stateName = fieldStateName.val();
	
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {maTinh: stateId, ten: stateName, datNuoc: {maDatnuoc: countryId, ten: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId) {
		$("#dropDownStates option:selected").text(stateName);
		showToastMessage("Tỉnh đã được cập nhật");
		changeFormStateToNew();
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});	
}

function addState() {
	if (!validateFormState()) return;
	
	url = contextPath + "tinhs/save";
	stateName = fieldStateName.val();
	
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {ten: stateName, datNuoc: {maDatnuoc: countryId, ten: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId) {
		selectNewlyAddedState(stateId, stateName);
		showToastMessage("Tỉnh mới đã được thêm vào");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});
		
}

function validateFormState() {
	formState = document.getElementById("formState");
	if (!formState.checkValidity()) {
		formState.reportValidity();
		return false;
	}	
	
	return true;
}

function selectNewlyAddedState(stateId, stateName) {
	$("<option>").val(stateId).text(stateName).appendTo(dropDownStates);
	
	$("#dropDownStates option[value='" + stateId + "']").prop("selected", true);
	
	fieldStateName.val("").focus();
}

function changeFormStateToNew() {
	buttonAddState.val("Thêm");
	labelStateName.text("Tên tiểu bang / tỉnh:");
	
	buttonUpdateState.prop("disabled", true);
	buttonDeleteState.prop("disabled", true);
	
	fieldStateName.val("").focus();	
}

function changeFormStateToSelectedState() {
	buttonAddState.prop("value", "Tạo mới");
	buttonUpdateState.prop("disabled", false);
	buttonDeleteState.prop("disabled", false);
	
	labelStateName.text("Tiểu bang / tỉnh được chọn:");
	
	selectedStateName = $("#dropDownStates option:selected").text();
	fieldStateName.val(selectedStateName);
	
}

function loadStates4Country() {
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "tinhs/list_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dropDownStates.empty();
		
		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.maTinh).text(state.ten).appendTo(dropDownStates);
		});
		
	}).done(function() {
		changeFormStateToNew();
		showToastMessage("Tất cả các trạng thái đã được tải cho quốc gia " + selectedCountry.text());
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});	
}

function loadCountries4States() {
	url = contextPath + "datnuocs/list";
	$.get(url, function(responseJSON) {
		dropDownCountry4States.empty();
		
		$.each(responseJSON, function(index, country) {
			$("<option>").val(country.maDatnuoc).text(country.ten).appendTo(dropDownCountry4States);
		});
		
	}).done(function() {
		buttonLoad4States.val("Làm mới danh sách quốc gia");
		showToastMessage("Tất cả các quốc gia đã được tải");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});
}