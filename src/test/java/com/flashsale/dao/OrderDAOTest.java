package com.flashsale.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flashsale.entity.Order;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class OrderDAOTest {
	
	@Resource
	private OrderDAO orderDAO;
	@Test
	public void testInsertOrder() {
		System.out.println(orderDAO.insertOrder(1001l, 13917556852l));
	}

	@Test
	public void testQueryOrderByIdWithProduct() {
		Order o = orderDAO.queryOrderByIdWithProduct(1000l, 13917556852l);
		System.out.println(o.toString());
	}

}
