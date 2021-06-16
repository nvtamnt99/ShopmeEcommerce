$(document).ready(function() {
	$("#buttonAdd2Cart").on("click", function(e) {
		addToCart();
	});		
});

function addToCart() {
	quantity = $("#quantity" + productId).val();
	url = contextPath + "cart/add/" + productId + "/" + quantity;
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(crsfHeaderName, csrfValue);
		}
	}).done(function(response) {
		//$("#modalTitle").text("Giỏ hàng");	
		//$("#modalBody").text(response);
		//$('#myModal').modal();
		showModalDialog("Giỏ hàng", response);
	}).fail(function() {
		//$("#modalTitle").text("Giỏ hàng");
		//$("#modalBody").text("Lỗi khi thêm sản phẩm vào giỏ hàng.");
		//$('#myModal').modal();
		showErrorModal("Lỗi khi thêm sản phẩm vào giỏ hàng.");
	});		
}