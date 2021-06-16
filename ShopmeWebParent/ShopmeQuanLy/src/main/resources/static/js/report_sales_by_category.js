// Report Sales by Category
$(document).ready(function(){
	$(".btn-sales-by-cat").on("click", function(e) {
		
		$(".btn-sales-by-cat").each(function(e) {
			$(this).removeClass('btn-primary').addClass('btn-light');
		});
		
		$(this).removeClass('btn-light').addClass('btn-primary');
		
		period = $(this).attr('period');		
		loadReportSalesByCategory(period);
	});
});


function loadReportSalesByCategory(period) {
	days = getDays(period);
	url = contextPath + "baocao/category/" + period;

	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Nhãn hiệu');
	data.addColumn('number', 'Tổng doanh thu');
	data.addColumn('number', 'Mạng lưới bán hàng');
	
	totalGrossSales = 0.0;
	totalNetSales = 0.0;
	totalProducts = 0;
	
	$.get(url, function(reportJson) {

		$.each(reportJson, function(index, reportItem) {
			data.addRows([[reportItem.dinhDanh, reportItem.tongDoanhThu, reportItem.mangLuoiBanhang]]);
			totalGrossSales += parseFloat(reportItem.tongDoanhThu);
			totalNetSales += parseFloat(reportItem.mangLuoiBanhang);
			totalProducts += parseInt(reportItem.soLuongSanpham);
		});		
		
		var options = {
				title: getChartTitle(period),
				'height': 360,
				legend: {position: 'right'}
		};
		
	    var formatter = new google.visualization.NumberFormat({
	    	prefix: 'đ'
	    });
	    formatter.format(data, 1);
	    formatter.format(data, 2);
	    
		var salesChart = new google.visualization.PieChart(document.getElementById('chart_sales_by_category'));
		salesChart.draw(data, options);	
		
		$("#textTotalGrossSales2").text($.number(totalGrossSales, 2)+ "đ");
		$("#textTotalNetSales2").text($.number(totalNetSales, 2)+"đ");
		
		$("#textAvgGrossSales2").text($.number(totalGrossSales / days, 2)+"đ");
		$("#textAvgNetSales2").text($.number(totalNetSales / days, 2)+"đ");
		
		$("#textTotalProducts1").text(totalProducts);	
		
		
	});
	
	
}