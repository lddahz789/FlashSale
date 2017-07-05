package com.flashsale.service;

import java.util.List;

import com.flashsale.dto.Exposer;
import com.flashsale.dto.FlashSaleExecution;
import com.flashsale.entity.Product;
import com.flashsale.exception.FlashSaleClosed;
import com.flashsale.exception.FlashSaleException;
import com.flashsale.exception.RepeatSaleException;

/**
 *
 * @author Zhuo He (Lyn)
 * @Date 2017-06-26
 *
 */
public interface FlashSaleService {
	
	List<Product> getProductsList();
	
	Product getProductById(long productId);
	

	/**
	 * 输出秒杀接口地址,否则输出系统时间
	 * @param productId
	 * @return
	 */
	Exposer exportFlashSaleUrl(long productId);
	
	/**
	 * 使用数据库存储过程执行秒杀
	 * @return
	 */
	FlashSaleExecution excuteFlashSaleProcedure(long productId,long userPhone, String md5);
	/**
	 * 执行秒杀操作
	 * @param priductId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws FlashSaleException
	 * @throws FlashSaleClosed
	 * @throws RepeatSaleException
	 * 操作结果封装 FlashSaleExecution
	 */
	FlashSaleExecution excuteFlashSale(long productId,long userPhone, String md5) 
			throws FlashSaleException,FlashSaleClosed,RepeatSaleException;
}
