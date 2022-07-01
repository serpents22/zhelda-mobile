package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class IndiviDataStatResponse{

	@SerializedName("is_fan")
	private boolean isFan;

	@SerializedName("is_alarm")
	private boolean isAlarm;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("tag_name")
	private String tagName;

	@SerializedName("temperature")
	private double temperature;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("th_info")
	private String thInfo;

	@SerializedName("id")
	private int id;

	public boolean isIsFan(){
		return isFan;
	}

	public boolean isIsAlarm(){
		return isAlarm;
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

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getThInfo() {
		return thInfo;
	}
}