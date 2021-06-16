var dropDownCountry;
var dataListState;
var fieldState;

$(document).ready(function() {
	dropDownCountry = $("#datNuoc");
	dataListState = $("#listStates");
	fieldState = $("#tinh");
	
	dropDownCountry.on("change", function() {
		loadStatesForCountry();
		fieldState.val("").focus();
	});
});

function loadStatesForCountry() {
	selectedCountry = $("#datNuoc option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "caidat/list_states_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dataListState.empty();
		
		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.ten).text(state.ten).appendTo(dataListState);
		});
		
	}).fail(function() {
		alert('không kết nối được với máy chủ!');
	});
}

function checkPasswordMatch(confirmPassword) {
	if (confirmPassword.value != $("#matKhau").val()) {
		confirmPassword.setCustomValidity("Mất khẩu không hợp lệ!");
	} else {
		confirmPassword.setCustomValidity("");
	}
}

function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showErrorModal(message) {
	showModalDialog("Error", message);
}

function showWarningModal(message) {
	showModalDialog("Warning", message);
}	