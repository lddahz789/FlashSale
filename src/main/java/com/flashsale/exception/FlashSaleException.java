package com.flashsale.exception;

public class FlashSaleException extends RuntimeException{

	public FlashSaleException(String message){
		super(message);
	}
	
	public FlashSaleException(String message, Throwable cause){
		super(message,cause);
	}
}
