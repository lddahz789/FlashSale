package com.flashsale.dao;

import java.util.Date;
import java.util.List;

import com.flashsale.entity.Product;

public interface ProductDAO {



	/**
	 * 通过ID减少产品库存
	 * @param productId
	 * @param saleTime
	 * @return
	 * 是否成功
	 */
	int reduceStock(long productId, Date saleTime);
	
	/**
	 * 通过ID获取1个产品对象
	 * @param productId
	 * @return
	 */
	Product queryById(long productId);
	
	/**
	 * 查询所有产品
	 * @return
	 */
	List<Product> queryAllProducts();
	
	/**
	 * 查询范围内产品
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Product> queryAllProducts(int offset, int limit);
}
