package com.flashsale.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flashsale.dto.Exposer;
import com.flashsale.dto.FlashSaleExecution;
import com.flashsale.dto.FlashSaleResult;
import com.flashsale.entity.Product;
import com.flashsale.enums.FlashSaleEnum;
import com.flashsale.exception.FlashSaleClosed;
import com.flashsale.exception.FlashSaleException;
import com.flashsale.exception.RepeatSaleException;
import com.flashsale.service.FlashSaleService;

@Controller
@RequestMapping("/flashsale")
public class FlashSaleController {

	static Logger log = Logger.getLogger(FlashSaleController.class);
	@Resource
	FlashSaleService service;

	/**
	 * 获取产品列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		// 获取列表页
		List<Product> productList = service.getProductsList();
		model.addAttribute("productList", productList);
		return "productList";
	}

	/**
	 * 获取指定产品的详细信息
	 * 
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{productId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("productId") Long productId, Model model) {

		if (productId == null) {
			return "redirect:/flashsale/list";
		}
		Product product = service.getProductById(productId);
		if (product == null) {
			return "redirect:/flashsale/list";
		}
		model.addAttribute("product", product);
		return "productDetail";
	}

	/**
	 * Ajax请求调用方法,返回查询秒杀API URL结果
	 * 
	 * @param productId
	 * @return
	 */
	
	@RequestMapping(value = "/{productId}/exposure", method = RequestMethod.POST)
    @ResponseBody
    public FlashSaleResult<Exposer> exposer(@PathVariable("productId") Long productId) {
        // 查询秒杀商品的结果
		FlashSaleResult<Exposer> result;
        try {
            Exposer exposer = service.exportFlashSaleUrl(productId);
            result = new FlashSaleResult<Exposer>(true, exposer);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            result = new FlashSaleResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


	/**
	 * 秒杀操作
	 * @param productId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	@RequestMapping(value = "/{productId}/{md5}/execution", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public FlashSaleResult<FlashSaleExecution> execute(@PathVariable("productId") Long productId,
			@CookieValue(value = "phone", required = false) Long userPhone, @PathVariable("md5") String md5) {
		
		if (userPhone == null){
			return new FlashSaleResult<FlashSaleExecution>(false,"非法用户!未注册");
		}
		FlashSaleResult<FlashSaleExecution> result;
		
		try {
			FlashSaleExecution flashSaleExecution = service.excuteFlashSale(productId, userPhone, md5);
			return new FlashSaleResult<FlashSaleExecution>(true,flashSaleExecution);
		} catch (FlashSaleClosed e) {
			FlashSaleExecution execution = new FlashSaleExecution(productId, FlashSaleEnum.END);
			return new FlashSaleResult<FlashSaleExecution>(true,execution);
		} catch (RepeatSaleException e) {
			FlashSaleExecution execution = new FlashSaleExecution(productId, FlashSaleEnum.REPEAT_SALE);
			return new FlashSaleResult<FlashSaleExecution>(true,execution);
		} catch (FlashSaleException e) {
			FlashSaleExecution execution = new FlashSaleExecution(productId, FlashSaleEnum.INNER_ERROR);
			return new FlashSaleResult<FlashSaleExecution>(true,execution);
		}
	}
	
	/**
	 * 返回服务器当前时间
	 * @return
	 */
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public FlashSaleResult<Long> nowTime(){
		Long time =  new Date().getTime();
		return new FlashSaleResult<Long>(true,time);
	}
}
