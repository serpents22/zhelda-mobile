package com.germeny.pasqualesilvio.model;

import com.google.gson.annotations.SerializedName;

public class IndiviDataListResponse{

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("device_model")
	private String deviceModel;

	@SerializedName("fw_info")
	private String fwInfo;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("tag_name")
	private String tagName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("th_name")
	private String thName;

	private IndiviDataStatResponse indiviDataStatResponse;
	private IndiviDataSetResponse indiviDataSetResponse;
	private IndiviDataDevStatResponse indiviDataDevStatResponse;

	public IndiviDataStatResponse getIndiviDataStatResponse() {
		return indiviDataStatResponse;
	}

	public void setIndiviDataStatResponse(IndiviDataStatResponse indiviDataStatResponse) {
		this.indiviDataStatResponse = indiviDataStatResponse;
	}

	public IndiviDataSetResponse getIndiviDataSetResponse() {
		return indiviDataSetResponse;
	}

	public void setIndiviDataSetResponse(IndiviDataSetResponse indiviDataSetResponse) {
		this.indiviDataSetResponse = indiviDataSetResponse;
	}

	public IndiviDataDevStatResponse getIndiviDataDevStatResponse() {
		return indiviDataDevStatResponse;
	}

	public void setIndiviDataDevStatResponse(IndiviDataDevStatResponse indiviDataDevStatResponse) {
		this.indiviDataDevStatResponse = indiviDataDevStatResponse;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setDeviceModel(String deviceModel){
		this.deviceModel = deviceModel;
	}

	public String getDeviceModel(){
		return deviceModel;
	}

	public void setFwInfo(String fwInfo){
		this.fwInfo = fwInfo;
	}

	public String getFwInfo(){
		return fwInfo;
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

	public void setThName(String thName){
		this.thName = thName;
	}

	public String getThName(){
		return thName;
	}
}