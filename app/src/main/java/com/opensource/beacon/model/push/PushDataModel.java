package com.opensource.beacon.model.push;

import java.util.List;

public class PushDataModel {
	private List<String> push_ids;
	private List<Object> localized_ids;
	private String operation_id;
	private List<Object> message_ids;
	private boolean ok;
	private List<Object> content_urls;

	public void setPushIds(List<String> pushIds){
		this.push_ids = pushIds;
	}

	public List<String> getPushIds(){
		return push_ids;
	}

	public void setLocalizedIds(List<Object> localizedIds){
		this.localized_ids = localizedIds;
	}

	public List<Object> getLocalizedIds(){
		return localized_ids;
	}

	public void setOperationId(String operationId){
		this.operation_id = operationId;
	}

	public String getOperationId(){
		return operation_id;
	}

	public void setMessageIds(List<Object> messageIds){
		this.message_ids = messageIds;
	}

	public List<Object> getMessageIds(){
		return message_ids;
	}

	public void setOk(boolean ok){
		this.ok = ok;
	}

	public boolean isOk(){
		return ok;
	}

	public void setContentUrls(List<Object> contentUrls){
		this.content_urls = contentUrls;
	}

	public List<Object> getContentUrls(){
		return content_urls;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"push_ids = '" + push_ids + '\'' +
			",localized_ids = '" + localized_ids + '\'' +
			",operation_id = '" + operation_id + '\'' +
			",message_ids = '" + message_ids + '\'' +
			",ok = '" + ok + '\'' + 
			",content_urls = '" + content_urls + '\'' +
			"}";
		}
}