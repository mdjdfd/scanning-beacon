package com.opensource.beacon.model;

public class RegionsItem{
	private String updated_at;
	private Geofence geofence;
	private String region_id;
	private String name;
	private String created_at;
	private SourceInfo source_info;

	public void setUpdatedAt(String updatedAt){
		this.updated_at = updatedAt;
	}

	public String getUpdatedAt(){
		return updated_at;
	}

	public void setGeofence(Geofence geofence){
		this.geofence = geofence;
	}

	public Geofence getGeofence(){
		return geofence;
	}

	public void setRegionId(String regionId){
		this.region_id = regionId;
	}

	public String getRegionId(){
		return region_id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.created_at = createdAt;
	}

	public String getCreatedAt(){
		return created_at;
	}

	public void setSourceInfo(SourceInfo sourceInfo){
		this.source_info = sourceInfo;
	}

	public SourceInfo getSourceInfo(){
		return source_info;
	}

	@Override
 	public String toString(){
		return 
			"RegionsItem{" + 
			"updated_at = '" + updated_at + '\'' +
			",geofence = '" + geofence + '\'' + 
			",region_id = '" + region_id + '\'' +
			",name = '" + name + '\'' + 
			",created_at = '" + created_at + '\'' +
			",source_info = '" + source_info + '\'' +
			"}";
		}
}
