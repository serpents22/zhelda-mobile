package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class Token{

	@SerializedName("type")
	private String type;

	@SerializedName("token")
	private String token;

	public String getType(){
		return type;
	}

	public String getToken(){
		return token;
	}
}