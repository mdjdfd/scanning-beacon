package com.opensource.beacon.model;

import java.util.List;

public class RegionsDataModel {
	private List<RegionsItem> regions;
	private int count;
	private boolean ok;

	public void setRegions(List<RegionsItem> regions){
		this.regions = regions;
	}

	public List<RegionsItem> getRegions(){
		return regions;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setOk(boolean ok){
		this.ok = ok;
	}

	public boolean isOk(){
		return ok;
	}

	@Override
 	public String toString(){
		return
			"RegionsDataModel{" +
			"regions = '" + regions + '\'' +
			",count = '" + count + '\'' +
			",ok = '" + ok + '\'' +
			"}";
		}
}