package com.flashsale.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.flashsale.dao.OrderDAO;
import com.flashsale.dao.ProductDAO;
import com.flashsale.dao.cache.RedisDAO;
import com.flashsale.dto.Exposer;
import com.flashsale.dto.FlashSaleExecution;
import com.flashsale.entity.Order;
import com.flashsale.entity.Product;
import com.flashsale.enums.FlashSaleEnum;
import com.flashsale.exception.FlashSaleClosed;
import com.flashsale.exception.FlashSaleException;
import com.flashsale.exception.RepeatSaleException;
import com.flashsale.service.FlashSaleService;

@Service
public class FlashSaleServiceImpl implements FlashSaleService {
	static Logger log = Logger.getLogger(FlashSaleServiceImpl.class);

	@Resource
	private ProductDAO productDAO;
	@Resource
	private OrderDAO orderDAO;
	@Resource
	private RedisDAO redisDAO;

	private final String chaos = "dwnad2982h88dh8**!*831nfan/1*daw~21DWcWWA";
	@Override
	public List<Product> getProductsList() {
		return productDAO.queryAllProducts();
	}
	@Override
	public Product getProductById(long productId) {
		return productDAO.queryById(productId);
	}
	@Override
	public Exposer exportFlashSaleUrl(long productId) {
		// 缓存优化操作在dao下的cache包中
		// 缓存操作
		Product product = redisDAO.getProduct(productId);
		if (product == null) {
			product = productDAO.queryById(productId);
			if (product == null) {
				return new Exposer(false, productId);
			} else {
				redisDAO.putProduct(product);
			}
		}

		Date startTime = product.getStartTime();
		Date endTime = product.getEndTime();
		Date now = new Date();

		if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
			return new Exposer(false, productId, now.getTime(), startTime.getTime(), endTime.getTime());
		}
		// 转化特定字符串,加密,不可逆
		String md5 = getMD5(productId);
		return new Exposer(true, md5, productId);
	}

	/*
	 * 事务方法
	 */
	@Override
	@Transactional
	public FlashSaleExecution excuteFlashSale(long productId, long userPhone, String md5)
			throws FlashSaleException, FlashSaleClosed, RepeatSaleException {

		if (md5 == null || !getMD5(productId).equals(md5)) {
			throw new FlashSaleException("检测到数据被修改,操作失败");
		}
		try {
			// 执行秒杀业务逻辑
			// 记录Order
			int orderResult = orderDAO.insertOrder(productId, userPhone);
			if (orderResult <= 0) {
				// rollback
				throw new RepeatSaleException("请勿重复提交操作!");
			} else {
				// 减少库存操作,行级锁竞争
				int reduceResult = productDAO.reduceStock(productId, new Date());
				if (reduceResult <= 0) {
					// rollback
					throw new FlashSaleClosed("秒杀活动已结束!");
				} else {
					// 秒杀成功 commit
					Order order = orderDAO.queryOrderByIdWithProduct(productId, userPhone);
					return new FlashSaleExecution(productId, FlashSaleEnum.SUCCESS, order);
				}

			}

		} catch (FlashSaleClosed e1) {
			throw e1;
		} catch (RepeatSaleException e2) {
			throw e2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FlashSaleException("内部错误: " + e.getMessage());
		}
	}

	private String getMD5(long productId) {
		String base = productId + "/" + chaos;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}

	@Override
	public FlashSaleExecution excuteFlashSaleProcedure(long productId, long userPhone, String md5) {
		if (md5 == null || !getMD5(productId).equals(md5)) {
			return new FlashSaleExecution(productId,FlashSaleEnum.DATA_ERROR);
		}
		Date saleTime = new Date();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("phone", userPhone);
		map.put("saleTime", saleTime);
		map.put("result", null);
		try {
			productDAO.saleByProcedure(map);
			int result = MapUtils.getInteger(map, "result",-2);
			if (result == 1){
				Order order = orderDAO.queryOrderByIdWithProduct(productId, userPhone);
				return new FlashSaleExecution(productId,FlashSaleEnum.SUCCESS,order);
			}else{
				return new FlashSaleExecution(productId,FlashSaleEnum.statusOf(result));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return new FlashSaleExecution(productId,FlashSaleEnum.INNER_ERROR);
		}
	}
}
