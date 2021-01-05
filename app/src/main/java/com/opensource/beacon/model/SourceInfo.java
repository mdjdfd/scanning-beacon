package com.opensource.beacon.model;

public class SourceInfo{
	private String region_id;
	private String source;
	private String vendor_href;


	public SourceInfo(){
	}

	public void setRegionId(String regionId){
		this.region_id = regionId;
	}

	public String getRegionId(){
		return region_id;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setVendorHref(String vendorHref){
		this.vendor_href = vendorHref;
	}

	public String getVendorHref(){
		return vendor_href;
	}

	@Override
 	public String toString(){
		return
			"SourceInfo{" +
			"region_id = '" + region_id + '\'' +
			",source = '" + source + '\'' +
			",vendor_href = '" + vendor_href + '\'' +
			"}";
		}
}
