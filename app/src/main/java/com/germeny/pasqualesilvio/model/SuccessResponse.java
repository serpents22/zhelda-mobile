package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class SuccessResponse{

	@SerializedName("code")
	private int code;

	@SerializedName("status")
	private String status;

	public int getCode(){
		return code;
	}

	public String getStatus(){
		return status;
	}
}