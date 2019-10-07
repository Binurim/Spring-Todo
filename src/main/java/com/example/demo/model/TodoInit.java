package com.example.demo.model;


public class TodoInit {
    private String summary;
    private String description;
	private String startTime;
	private String endTime;
	private String startDate;
	private String endDate;
    
	public TodoInit(String summary, String description, String startDate, String endDate, String startTime,
			String endTime) {
		this.summary = summary;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;	
		this.startDate= startDate;
		this.endDate= endDate;

	}

	public String getSummary() {
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime ;
	}
	public String getEndTime() {
		return startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate ;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	   
}
