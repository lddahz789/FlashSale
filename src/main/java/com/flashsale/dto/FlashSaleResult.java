package com.flashsale.dto;

/**
 * 封装json结果
 * @author Zhuo He (Lyn)
 * @Date 2017-06-27
 *
 */
public class FlashSaleResult<T> {
	private boolean succeeded;

    private T data;

    private String error;

    public FlashSaleResult() {
    }

    public FlashSaleResult(boolean succeeded, T data) {
        this.succeeded = succeeded;
        this.data = data;
    }

    public FlashSaleResult(boolean succeeded, String error) {
        this.succeeded = succeeded;
        this.error = error;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean success) {
        this.succeeded = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SeckillResult{" +
                "状态 = " + succeeded +
                ", 数据 = " + data +
                ", 错误消息 = '" + error + '\'' +
                '}';
    }
}
