package com.cloud.luis.common.model;

import lombok.Data;

/**
 * 
 * @author lyf
 *
 */

@Data
public class ReturnData<T> {
	
	private int code;
	
	private String msg;
	
	private T result;
	
	public ReturnData() {
		super();
	}
	
	public ReturnData(int code, String msg, T result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	
	public ReturnData(T result) {
		this.code = 1;
		this.msg = "success";
		this.result = result;
	}
	
	public ReturnData(Throwable e) {
		this.code = 0;
		this.msg = e.getMessage();
	}

}
