package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class DevChronaResponse{

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("tag_name")
	private String tagName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("value")
	private String value;

	public String getDeviceId(){
		return deviceId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getTagName(){
		return tagName;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getValue(){
		return value;
	}
}