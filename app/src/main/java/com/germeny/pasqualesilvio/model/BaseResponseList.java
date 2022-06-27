package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponseList<T> {

	@SerializedName("code")
	private int code;

	@SerializedName("status")
	private String status;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
	private List<T> data;

	public int getCode(){
		return code;
	}

	public String getStatus(){
		return status;
	}

	public String getMessage(){
		return message;
	}

	public List<T> getData(){
		return data;
	}
}