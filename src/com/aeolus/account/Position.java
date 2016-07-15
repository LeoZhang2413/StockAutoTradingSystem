package com.aeolus.account;

public class Position {
	private String contractID;
	private PositionType positionType;
	private double positionValue;
	private int position;
	public Position(String contractID, PositionType positionType){
		this.contractID = contractID;
		this.positionType = positionType;
	}
	public double averagePoisitionPrice(){
		return positionValue/position;
	}
	public PositionType getPositionType() {
		return positionType;
	}
	public void setPositionType(PositionType positionType) {
		this.positionType = positionType;
	}
	public double getPositionValue() {
		return positionValue;
	}
	public void setPositionValue(double positionValue) {
		this.positionValue = positionValue;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getContractID() {
		return contractID;
	}
	public void addLongPosition(int position, double price){
		this.position +=position;
		this.positionValue += position*price;
	}
	public int substractLongPosition(int position, double price){
		if(this.position<position){
			position = this.position;
		}
		positionValue = averagePoisitionPrice()*(this.position-position);
		this.position -= position;
		return position;
	}
	public String toString(){
		return "ID: "+contractID+" Type: "+positionType+" averagePoisitionPrice: "+averagePoisitionPrice()+" amount: "+position;
	}
}
