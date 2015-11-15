package com.aeolus.resources.data;

import java.io.Serializable;
import java.util.Date;

import com.aeolus.util.MyUtil;

public class Quote implements Serializable{
	private Date time;
	private double open;
	private double close;
	private double high;
	private double low;
	private double volume;
	public Quote(Date time,double open, double close, double high, double low, double volume){
		this.time = time;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}
	public double getOpen() {
		return open;
	}
	public double getClose() {
		return close;
	}
	public double getHigh() {
		return high;
	}
	public double getLow() {
		return low;
	}
	public double getVolume() {
		return volume;
	}
	public Date getTime(){
		return time;
	}
	public String toString(){
		return " Date: "+time.toString()+" close: "+close;
	}
}
