package com.shopme.common.entity;

public enum TinhTrangDonHang {
	NEW {
		public String getDescription() {
			return "Đơn hàng đã được đặt bởi khách hàng";
		}
	},
	
	HUY {
		public String getDescription() {
			return "Đơn hàng đã bị hủy";
		}		
	}, 
	
	XU_LY {
		public String getDescription() {
			return "Yêu cầu đang được xử lý";
		}		
	},
	
	DONG_GOI {
		public String getDescription() {
			return "Sản phẩm được đóng gói để vận chuyển";
		}		
	},
	
	DA_DONG {
		public String getDescription() {
			return "Người giao hàng đã chọn gói hàng";
		}		
	},
	
	VAN_CHUYEN {
		public String getDescription() {
			return "Gói hàng đang được chuyển đến";
		}		
	},
	
	GIAO_HANG {
		public String getDescription() {
			return "Gói hàng đã được giao";
		}		
	},
	
	TRA_VE {
		public String getDescription() {
			return "Gói hàng đã được trả lại";
		}		
	},
	
	THANH_TOAN {
		public String getDescription() {
			return "Khách hàng đã thanh toán đơn đặt hàng này";
		}		
	},
	
	HOAN_TRA {
		public String getDescription() {
			return "Đơn đặt hàng đã được hoàn lại cho khách hàng";
		}		
	};
	
	public String getDescription() {
		return "";
	}
}
