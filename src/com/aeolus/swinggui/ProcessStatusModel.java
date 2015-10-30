package com.aeolus.swinggui;

public class ProcessStatusModel extends AbstractModelObject{
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		int old_value = this.value;
		this.value = value;
		firePropertyChange("value", old_value, value);
	}
}
