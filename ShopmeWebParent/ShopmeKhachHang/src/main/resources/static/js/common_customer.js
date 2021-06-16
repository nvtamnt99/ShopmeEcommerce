var dropdownCountries;
var dropdownStates;

$(document).ready(function() {
	dropdownCountries = $("#datNuoc");
	dropdownStates = $("#listStates");

	dropdownCountries.on("change", function() {
		loadStates4Country();
		$("#tinh").val("").focus();
	});		
});

function loadStates4Country() {
	selectedCountry = $("#datNuoc option:selected");
	countryId = selectedCountry.val();
	
	url = contextPath + "caidat/list_states_by_country/" + countryId;
	
	$.get(url, function(responseJson) {
		dropdownStates.empty();
		
		$.each(responseJson, function(index, state) {
			$("<option>").val(state.ten).text(state.ten).appendTo(dropdownStates);
		});
	}).done(function() {
	})	
}	

function checkPasswordMatch(fieldRetypePassword) {
	if (fieldRetypePassword.value != $("#password").val()) {
		fieldRetypePassword.setCustomValidity("Passwords do not match!");
	} else {
		fieldRetypePassword.setCustomValidity("");
	}
}	