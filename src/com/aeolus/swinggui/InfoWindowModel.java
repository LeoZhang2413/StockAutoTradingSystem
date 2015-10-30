package com.aeolus.swinggui;

public class InfoWindowModel extends AbstractModelObject{
	private String date;
	private String open;
	private String close;
	private String high;
	private String low;
	private String volume;
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		String old_value = this.open;
		this.open = open;
		firePropertyChange("open", old_value, open);
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		String old_value = this.close;
		this.close = close;
		firePropertyChange("close", old_value, close);
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		String old_value = this.high;
		this.high = high;
		firePropertyChange("high", old_value, high);
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		String old_value = this.low;
		this.low = low;
		firePropertyChange("low", old_value, low);
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		String old_value = this.volume;
		this.volume = volume;
		firePropertyChange("volume", old_value, volume);
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		String old_value = this.date;
		this.date = date;
		firePropertyChange("date", old_value, date);
	}
}
