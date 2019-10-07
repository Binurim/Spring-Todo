package com.example.demo.model;

public class Todo { 
    private String id;
    private String summary;
    private String description;
	private String startDateTime;
	private String endDateTime;
    
	public Todo(String id, String summary, String description, String string , String string1) {
		this.id = id;
		this.summary = summary;
		this.description = description;
		this.startDateTime = string;
		this.endDateTime = string1;	
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getsummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getstartDateTime() {
		return startDateTime;
	}
	public void setstartDateTime(String string) {
		this.startDateTime = startDateTime ;
	}
	public String getendDateTime() {
		return endDateTime;
	}
	public void setendDateTime(String string1) {
		this.endDateTime = endDateTime;
	}
	  
}
