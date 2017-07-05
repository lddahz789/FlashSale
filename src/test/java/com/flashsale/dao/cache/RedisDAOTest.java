package com.flashsale.dao.cache;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flashsale.dao.ProductDAO;
import com.flashsale.entity.Product;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class RedisDAOTest {
	private final Logger log = Logger.getLogger(RedisDAOTest.class);
	private long id = 1003;
	@Resource
	RedisDAO redisDAO;
	@Resource
	ProductDAO productDAO;
	@Test
	public void testRedis() {
		Product product = redisDAO.getProduct(id);
		if (product == null){
			product = productDAO.queryById(id);
			if(product != null){
				String result = redisDAO.putProduct(product);
				System.out.println("1号结果: " + result);
				product  = redisDAO.getProduct(id);
				System.out.println("2号结果: " + product.getName());
			}
		}
	}

}
