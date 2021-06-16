// Report Sales by Product
$(document).ready(function(){
	$(".btn-sales-by-product").on("click", function(e) {
		
		$(".btn-sales-by-product").each(function(e) {
			$(this).removeClass('btn-primary').addClass('btn-light');
		});
		
		$(this).removeClass('btn-light').addClass('btn-primary');
		
		period = $(this).attr('period');		
		loadReportSalesByProduct(period);
	});
});


function loadReportSalesByProduct(period) {
	days = getDays(period);
	url = contextPath + "baocao/product/" + period;

	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Sản phẩm');
	data.addColumn('number', 'Số lượng');
	data.addColumn('number', 'Tổng doanh thu');
	data.addColumn('number', 'Mạng lưới bán hàng');
	
	totalGrossSales = 0.0;
	totalNetSales = 0.0;
	totalProducts = 0;
	
	$.get(url, function(reportJson) {

		$.each(reportJson, function(index, reportItem) {
			data.addRows([[reportItem.dinhDanh, reportItem.soLuongSanpham, reportItem.tongDoanhThu, reportItem.mangLuoiBanhang]]);
			totalGrossSales += parseFloat(reportItem.tongDoanhThu);
			totalNetSales += parseFloat(reportItem.mangLuoiBanhang);
			totalProducts += parseInt(reportItem.soLuongSanpham);
		});		
		
		var options = {
				title: getChartTitle(period),
				'height': 360,
				showRowNumber: true,
				page: 'enable',
				sortColumn: 2,
				sortAscending: false,
		};
		
	    var formatter = new google.visualization.NumberFormat({
	    	prefix: 'đ'
	    });
	    formatter.format(data, 2);
	    formatter.format(data, 3);		
		
		var salesChart = new google.visualization.Table(document.getElementById('chart_sales_by_product'));
		salesChart.draw(data, options);	
		
		$("#textTotalGrossSales3").text($.number(totalGrossSales, 2)+"đ");
		$("#textTotalNetSales3").text($.number(totalNetSales, 2)+"đ");
		
		$("#textAvgGrossSales3").text($.number(totalGrossSales / days, 2)+"đ");
		$("#textAvgNetSales3").text($.number(totalNetSales / days, 2)+"đ");
		
		$("#textTotalProducts2").text(totalProducts);		
		
		
	});
	
	
}