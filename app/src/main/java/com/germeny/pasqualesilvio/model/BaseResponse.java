package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

	@SerializedName("code")
	private int code;

	@SerializedName("status")
	private String status;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
	private T data;

	public int getCode(){
		return code;
	}

	public String getStatus(){
		return status;
	}

	public String getMessage(){
		return message;
	}

	public T getData(){
		return data;
	}
}