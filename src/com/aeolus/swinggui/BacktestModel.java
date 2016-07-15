package com.aeolus.swinggui;

public class BacktestModel extends AbstractModelObject{
	private String signalInfo;

	public String getSignalInfo() {
		return signalInfo;
	}

	public void setSignalInfo(String signalInfo) {
		String old_value = this.signalInfo;
		this.signalInfo = signalInfo;
		firePropertyChange("signalInfo", old_value, signalInfo);
	}
}
