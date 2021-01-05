package com.opensource.beacon.model;

public class Geofence{
	private Center center;
	private String type;
	private int radius;

	public void setCenter(Center center){
		this.center = center;
	}

	public Center getCenter(){
		return center;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setRadius(int radius){
		this.radius = radius;
	}

	public int getRadius(){
		return radius;
	}

	@Override
 	public String toString(){
		return 
			"Geofence{" + 
			"center = '" + center + '\'' + 
			",type = '" + type + '\'' + 
			",radius = '" + radius + '\'' + 
			"}";
		}
}
