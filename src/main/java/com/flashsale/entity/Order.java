package com.flashsale.entity;

import java.util.Date;

public class Order {
	private long productId;
	private long userPhone;
	private short status;
	private Date createTime;
	private Product product;

	public Order() {
	}

	public Order(long productId, long userPhone, short status, Date createTime, Product product) {
		super();
		this.productId = productId;
		this.userPhone = userPhone;
		this.status = status;
		this.createTime = createTime;
		this.product = product;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Order [productId=" + productId + ", userPhone=" + userPhone + ", status=" + status + ", createTime="
				+ createTime + ", product=" + product + "]";
	}

}
