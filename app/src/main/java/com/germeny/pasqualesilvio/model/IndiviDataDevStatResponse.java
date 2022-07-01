package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class IndiviDataDevStatResponse{

	@SerializedName("chrono2_stat")
	private int chrono2Stat;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("is_alarmed")
	private boolean isAlarmed;

	@SerializedName("chrono1_stat")
	private int chrono1Stat;

	@SerializedName("id")
	private int id;

	public int getChrono2Stat(){
		return chrono2Stat;
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

	public boolean isIsAlarmed(){
		return isAlarmed;
	}

	public int getChrono1Stat(){
		return chrono1Stat;
	}

	public int getId(){
		return id;
	}
}