package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse{

	@SerializedName("code")
	private int code;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}