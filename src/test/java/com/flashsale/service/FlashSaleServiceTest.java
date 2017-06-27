package com.flashsale.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flashsale.dto.Exposer;
import com.flashsale.dto.FlashSaleExecution;
import com.flashsale.entity.Product;
import com.flashsale.exception.FlashSaleClosed;
import com.flashsale.exception.RepeatSaleException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })

public class FlashSaleServiceTest {

	static Logger log = Logger.getLogger(FlashSaleServiceTest.class);
	@Resource
	FlashSaleService service;

	@Test
	public void testGetProductsList() {
		List<Product> ps = service.getProductsList();
		log.info(ps);
	}

	@Test
	public void testGetProductById() {
		log.info(service.getProductById(1001l));
	}

	@Test
	public void testExcuteProcess() {
		long productId = 10033l;
		Exposer exposer = service.exportFlashSaleUrl(productId);
		if (exposer.isExposed()) {
			String md5 = exposer.getMd5();
			long phone = 13917556852l;
			try {
				FlashSaleExecution result = service.excuteFlashSale(productId, phone, md5);
				log.info(result);
			} catch (RepeatSaleException e) {
				log.error(e.getMessage());
			} catch (FlashSaleClosed e) {
				log.error(e.getMessage());
			}
		} else {
			log.warn("ÃëÉ±»¹Î´¿ªÆô: " + exposer);
		}
	}

}
