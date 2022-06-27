package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse{
	@SerializedName("gatewaysData")
	private List<GatewaysDataItem> gatewaysData;

	@SerializedName("token")
	private Token token;

	public List<GatewaysDataItem> getGatewaysData(){
		return gatewaysData;
	}

	public Token getToken(){
		return token;
	}
}