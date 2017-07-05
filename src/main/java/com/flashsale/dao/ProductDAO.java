package com.flashsale.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.flashsale.entity.Product;



/**
 *
 * @author Zhuo He (Lyn)
 * @Date 2017-07-04
 *
 */
public interface ProductDAO {



	/**
	 * 通过ID减少产品库存
	 * @param productId
	 * @param saleTime
	 * @return
	 * 是否成功
	 */
	int reduceStock(@Param("productId") long productId, @Param("saleTime") Date saleTime);
	
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
	List<Product> queryAllProducts(@Param("offset") int offset, @Param("limit") int limit);
	
	
	/**
	 * 使用数据库存储过程进行秒杀
	 * 
	 * @param paramMap
	 */
	void saleByProcedure(Map<String,Object> paramMap);
}
