package com.flashsale.exception;

/**
 * Run time exception
 * @author Zhuo He (Lyn)
 * @Date 2017-06-26
 *
 */
public class RepeatSaleException extends FlashSaleException {
	
	public RepeatSaleException(String message){
		super(message);
	}
	
	public RepeatSaleException(String message, Throwable cause){
		super(message,cause);
	}
}
