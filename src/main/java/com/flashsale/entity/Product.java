package com.flashsale.entity;

import java.util.Date;

public class Product {
	private long productId;
	private String name;
	private int stock;
	private Date startTime;
	private Date endTime;
	private Date createTime;

	public Product() {
	}

	public Product(long productId, String name, int stock, Date startTime, Date endTime, Date createTime) {
		super();
		this.productId = productId;
		this.name = name;
		this.stock = stock;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;

	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", stock=" + stock + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", createTime=" + createTime + "]";
	}

}
