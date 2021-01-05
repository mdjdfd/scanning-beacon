package com.opensource.beacon.model;

public class Center{
	private double latitude;
	private double longitude;

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return
			"Center{" +
			"latitude = '" + latitude + '\'' +
			",longitude = '" + longitude + '\'' +
			"}";
		}
}
