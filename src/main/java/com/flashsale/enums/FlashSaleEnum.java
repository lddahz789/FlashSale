package com.flashsale.enums;

/**
 * 使用枚举表示常量数据
 * @author Zhuo He (Lyn)
 * @Date 2017-06-27
 *
 */
public enum FlashSaleEnum {
	SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_SALE(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_ERROR(-3, "数据篡改");
	
	private int status;
	private String statusInfo;
	
	private FlashSaleEnum(int status, String statusInfo) {
		this.status = status;
		this.statusInfo = statusInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}
	
	public static FlashSaleEnum statusOf(int index){
		for (FlashSaleEnum status : values()) {
			if (status.getStatus() == index){
				return status;
			}
		}
		return null;
	}
}
