package com.flashsale.dto;

/**
 * 暴露秒杀接口地址
 * 
 * @author Zhuo He (Lyn)
 * @Date 2017-06-26
 * 
 */
public class Exposer {
	// 秒杀开启状态
	private boolean isExposed;
	// 加密接口地址
	private String md5;
	private long productId;
	// 系统当前时间,毫秒
	private long now;
	private long start;
	private long end;

	public Exposer(boolean isExposed, String md5, long productId) {
		super();
		this.isExposed = isExposed;
		this.md5 = md5;
		this.productId = productId;
	}

	public Exposer(boolean isExposed, long productId,long now, long start, long end) {
		super();
		this.isExposed = isExposed;
		this.now = now;
		this.productId = productId;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean isExposed, long productId) {
		super();
		this.isExposed = isExposed;
		this.productId = productId;
	}

	public Exposer(boolean isExposed, String md5, long productId, long now, long start, long end) {
		super();
		this.isExposed = isExposed;
		this.md5 = md5;
		this.productId = productId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public boolean isExposed() {
		return isExposed;
	}

	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exposer [isExposed=" + isExposed + ", md5=" + md5 + ", productId=" + productId + ", now=" + now
				+ ", start=" + start + ", end=" + end + "]";
	}

}
