$(document).ready(function(){
	$(".btn-sales-by-date").on("click", function(e) {
		
		$(".btn-sales-by-date").each(function(e) {
			$(this).removeClass('btn-primary').addClass('btn-light');
		});
		
		$(this).removeClass('btn-light').addClass('btn-primary');
		
		period = $(this).attr('period');		
		loadReportSalesByDate(period);
	});
});


function loadReportSalesByDate(period) {
	days = getDays(period);
	url = contextPath + "baocao/sales_by_date/" + period;

	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Ngày');
	data.addColumn('number', 'Tổng doanh thu');
	data.addColumn('number', 'Mạng lưới bán hàng');
	data.addColumn('number', 'Đơn hàng');
	
	totalGrossSales = 0.0;
	totalNetSales = 0.0;
	totalOrders = 0;
	
	$.get(url, function(reportJson) {

		$.each(reportJson, function(index, reportItem) {
			data.addRows([[reportItem.dinhDanh, reportItem.tongDoanhThu, reportItem.mangLuoiBanhang, reportItem.soLuongDonhang]]);
			totalGrossSales += parseFloat(reportItem.tongDoanhThu);
			totalNetSales += parseFloat(reportItem.mangLuoiBanhang);
			totalOrders += parseInt(reportItem.soLuongDonhang);
		});		
		
		var options = {
				title: getChartTitle(period),
				'height': 360,
				legend: {position: 'top'},
	        series: {
	            0: {targetAxisIndex: 0},
	            1: {targetAxisIndex: 0},
	            2: {targetAxisIndex: 1}
	          },
	          vAxes: {
	            // Adds titles to each axis.
	            0: {title: 'Sản lượng bán ra', currency : 'currency'},
	            1: {title: 'Số lượng đơn đặt hàng'}
	          }		
		};
		
	    var formatter = new google.visualization.NumberFormat({
	    	prefix: 'đ',
	    });
	    formatter.format(data, 1);
	    formatter.format(data, 2);
		
		var salesChart = new google.visualization.ColumnChart(document.getElementById('chart_sales_by_date'));
		salesChart.draw(data, options);	
		
		$("#textTotalGrossSales1").text($.number(totalGrossSales, 2)+"đ");
		$("#textTotalNetSales1").text($.number(totalNetSales, 2)+"đ");
		
		$("#textAvgGrossSales1").text($.number(totalGrossSales / days, 2)+"đ");
		$("#textAvgNetSales1").text($.number(totalNetSales / days, 2)+"đ");
		
		$("#textTotalOrders").text(totalOrders);
		
	});
	
	
}

function getDays(period) {
	if (period == 'last_7_days') return 7;
	if (period == 'last_28_days') return 28;
	if (period == 'last_6_months') return 6;
	if (period == 'last_year') return 12;
	
	return 7;
}

function getChartTitle(period) {
	if (period == 'last_7_days') return "Doanh số bán hàng trong 7 ngày qua";
	if (period == 'last_28_days') return "Doanh số bán hàng trong 28 ngày qua";
	if (period == 'last_6_months') return "Doanh số bán hàng trong 6 tháng qua";
	if (period == 'last_year') return "Doanh số bán hàng trong năm ngoái";
	
	return "";
}