package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class IndiviDataResponse {

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("is_on")
	private boolean isOn;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("n_sectors")
	private int nSectors;

	@SerializedName("id")
	private int id;

	@SerializedName("is_heater")
	private boolean isHeater;

	@SerializedName("is_chrono")
	private boolean isChrono;

	public String getDeviceId(){
		return deviceId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public boolean isIsOn(){
		return isOn;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getNSectors(){
		return nSectors;
	}

	public int getId(){
		return id;
	}

	public boolean isIsHeater(){
		return isHeater;
	}

	public boolean isIsChrono(){
		return isChrono;
	}
}