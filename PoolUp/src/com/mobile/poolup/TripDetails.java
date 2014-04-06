package com.mobile.poolup;

public class TripDetails {
	
	private String name,time,date,dest;
	
	public TripDetails(String name,String time,String date, String dest){
		this.name=name;
		this.date=date;
		this.dest=dest;
		this.time=time;
		
	}

	public String getName() {
		return name;
	}

	public String getTime() {
		return time;
	}

	public String getDate() {
		return date;
	}

	public String getDest() {
		return dest;
	}

}
