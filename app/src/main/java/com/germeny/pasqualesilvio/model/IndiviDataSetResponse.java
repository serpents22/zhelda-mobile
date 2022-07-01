package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class IndiviDataSetResponse{

	@SerializedName("group_mask")
	private String groupMask;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("tag_name")
	private String tagName;

	@SerializedName("temperature")
	private double temperature;

	@SerializedName("is_on")
	private boolean isOn;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("th_info")
	private String thInfo;

	public String getGroupMask(){
		return groupMask;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public String getTagName(){
		return tagName;
	}

	public double getTemperature(){
		return temperature;
	}

	public boolean isIsOn(){
		return isOn;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getThInfo(){
		return thInfo;
	}
}