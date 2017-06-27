package com.flashsale.exception;

public class FlashSaleClosed extends FlashSaleException{
	
	public FlashSaleClosed(String message){
		super(message);
	}
	
	public FlashSaleClosed(String message, Throwable cause){
		super(message,cause);
	}
}
