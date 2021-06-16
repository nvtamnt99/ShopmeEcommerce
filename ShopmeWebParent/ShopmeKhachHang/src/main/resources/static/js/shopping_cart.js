decimalSeparator = decimalPointType == 'COMMA' ? ',' : '.';
thousandsSeparator = thousandsPointType == 'COMMA' ? ',' : '.'; 
$(document).ready(function() {	
	$(".link-remove").on("click", function(e) {
		e.preventDefault();
		removeFromCart($(this));
	});	
	
	$(".minusButton").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this));
	});
	
	$(".plusButton").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));
	});	
	
	updateTotal();
});

function removeFromCart(link) {
	url = link.attr("href");
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(crsfHeaderName, csrfValue);
		}
	}).done(function(response) {
		$("#modalTitle").text("Giỏ Hàng");
		if (response.includes("removed")) {
			$("#myModal").on("hide.bs.modal", function(e) {
				rowNumber = link.attr('rowNumber');
				removeProduct(rowNumber);
				updateCountNumbers(); 
				updateTotal();
			});			
		}
		
		$("#modalBody").text(response);
		$('#myModal').modal();
	}).fail(function() {
		$("#modalTitle").text("Giỏ hàng");
		$("#modalBody").text("Lỗi khi xóa sản phẩm khỏi giỏ hàng.");
		$('#myModal').modal();
	});	
}

function removeProduct(rowNumber) {
	rowId = "row" + rowNumber;
	$("#" + rowId).remove();
}

function updateCountNumbers() {
	$(".div-count").each(function (index, element) {
		element.innerHTML = "" + (index + 1);
	});
}

function increaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	newQty = parseInt(qtyInput.val()) - 1;
	if (newQty > 0) {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
}

function decreaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);		
	newQty = parseInt(qtyInput.val()) + 1;
	if (newQty < 10) {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
}

function updateQuantity(productId, quantity) {
	url = contextPath + "cart/update/" + productId + "/" + quantity;
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(crsfHeaderName, csrfValue);
		}
	}).done(function(newSubtotal) {
		updateSubtotal(newSubtotal, productId);
		updateTotal();
	}).fail(function() {
		$("#modalTitle").text("Giỏ Hàng");
		$("#modalBody").text("Lỗi Không thể chỉnh sửa số lượng");
		$('#myModal').modal();
	});	
}

function updateSubtotal(newSubtotal	, productId) {
	// formattedSubtotal = $.number(newSubtotal, 2);
	$("#subtotal" + productId).text(formatCurrency(newSubtotal));
	//$("#subtotal" + productId).text(newSubtotal);
}

function updateTotal() {
	total = 0.0;
	productCount = 0;

	$(".productSubtotal").each(function(index, element) {
		productCount++;
		total += parseFloat(clearCurrencyFormat(element.innerHTML));
	});

	 if (productCount < 1) {
		hideSectionTotal();
	} else {
		//formattedTotal = $.number(total, 2);
		$("#total").text(formatCurrency(total));
	}
}

function hideSectionTotal() {
 	$("#sectionTotal").hide();
 	$("#sectionEmptyCart").removeClass("d-none");
 }
 function formatCurrency(amount) {
	return $.number(amount, decimalDigits, decimalSeparator, thousandsSeparator);
}

function clearCurrencyFormat(numberString) {
	result = numberString.replaceAll(thousandsSeparator, "");
	return result.replaceAll(decimalSeparator, ".");
}