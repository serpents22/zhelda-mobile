package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class DayNightResponse{

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("stop")
	private String stop;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("tag_name")
	private String tagName;

	@SerializedName("start")
	private String start;

	@SerializedName("week_mask")
	private String weekMask;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setStop(String stop){
		this.stop = stop;
	}

	public String getStop(){
		return stop;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public String getTagName(){
		return tagName;
	}

	public void setStart(String start){
		this.start = start;
	}

	public String getStart(){
		return start;
	}

	public void setWeekMask(String weekMask){
		this.weekMask = weekMask;
	}

	public String getWeekMask(){
		return weekMask;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}