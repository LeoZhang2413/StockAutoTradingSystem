package com.aeolus.strategy;

import java.util.Date;

import com.ib.client.Contract;

public class TradingSignal {
	private SignalType type;
	private Contract contract;
	private Date time;
	private double ratio;
	private double price;
	public TradingSignal(SignalType type, Contract contract, Date time, double ratio, double price) {
		super();
		this.type = type;
		this.contract = contract;
		this.time = time;
		this.ratio = ratio;
		this.price = price;
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
	public double getRatio() {
		return ratio;
	}
}
