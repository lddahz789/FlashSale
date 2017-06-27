package com.flashsale.dao;

import org.apache.ibatis.annotations.Param;

import com.flashsale.entity.Order;

public interface OrderDAO {
	
	/**
	 * 插入一条记录
	 * @param productId
	 * @param userPhone
	 * @return
	 * 状态 成功与否
	 */
	int insertOrder(@Param("productId")long productId, @Param("userPhone")long userPhone);

	/**
	 * 查询一个Order
	 * @param productId
	 * @return
	 */
	Order queryOrderByIdWithProduct(@Param("productId")long productId, @Param("userPhone")long userPhone);
}
