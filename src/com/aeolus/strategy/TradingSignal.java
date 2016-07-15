package com.aeolus.strategy;

import java.util.Date;

import com.ib.client.Contract;

public class TradingSignal {
	private SignalType type;
	private Contract contract;
	private Date time;
	private double price;
	private int volumn;
	public TradingSignal(SignalType type, Contract contract, Date time, int volumn, double price) {
		super();
		this.type = type;
		this.contract = contract;
		this.time = time;
		this.volumn = volumn;
		this.price = price;
	}
	public void setType(SignalType type) {
		this.type = type;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}
	public int getVolumn() {
		return volumn;
	}
	public double getPrice() {
		return price;
	}
	public SignalType getType() {
		return type;
	}
	public Contract getContract() {
		return contract;
	}
	public Date getTime() {
		return time;
	}
	public String getSignalInfo(){
		if(!getType().equals(SignalType.FAKE)){
			return "type: "+getType()+"\nprice: "+ getPrice()+"\nvolumn: "+getVolumn();
		}
		return "no record";
	}
	public String toString(){
		return "type: "+type+" contract: "+contract.m_symbol + " time: "+time.toString()+" price: "+price+" volumn: "+volumn;
	}
}
