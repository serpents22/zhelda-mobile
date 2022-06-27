package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class GatewaysDataItem{

	@SerializedName("max_devices")
	private int maxDevices;

	@SerializedName("device_name")
	private String deviceName;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("type")
	private String type;

	public int getMaxDevices(){
		return maxDevices;
	}

	public String getDeviceName(){
		return deviceName;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getType(){
		return type;
	}
}