package com.flashsale.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flashsale.entity.Product;
/**
 * 配置spring和junit整合，这样junit在启动时就会加载spring容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class ProductDAOTest {
	
	@Resource
	private ProductDAO productDAO;
	
	
	@Test
	public void testQueryById() {
		long id = 1000;
		Product p = productDAO.queryById(id);
		System.out.println(p.toString());
	}

	@Test
	public void testQueryAllProducts() {
		List<Product> ps = productDAO.queryAllProducts();
		for (Product product : ps) {
			System.out.println(product.getName());
		}
	}

	@Test
	public void testQueryAllProductsIntInt() {
		List<Product> ps = productDAO.queryAllProducts(0,3);
		for (Product product : ps) {
			System.out.println(product.getName());
		}
	}
	@Test
	public void testReduceStock() {
		
		System.out.println(productDAO.reduceStock(1000l, new Date()));
		
	}

	

}
