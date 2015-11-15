package com.aeolus.backtesting;

import java.util.Date;

import com.aeolus.account.CashAccount;

public class BacktestReport {
	private double annualSharpeRatio;
	private double maxDrawDown;
	private double maxDrawDownDuration;
	private Date startDate;
	private Date endDate;
	private CashAccount finalAccount;
	public BacktestReport(double initialAccountNetWorth,Date startDate, Date endDate) {
		super();
		this.finalAccount = new CashAccount(initialAccountNetWorth);
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public CashAccount getFinalAccount() {
		return finalAccount;
	}
	public double getAnnualSharpeRatio() {
		return annualSharpeRatio;
	}
	public void setAnnualSharpeRatio(double annualSharpeRatio) {
		this.annualSharpeRatio = annualSharpeRatio;
	}
	public double getMaxDrawDown() {
		return maxDrawDown;
	}
	public void setMaxDrawDown(double maxDrawDown) {
		this.maxDrawDown = maxDrawDown;
	}
	public double getMaxDrawDownDuration() {
		return maxDrawDownDuration;
	}
	public void setMaxDrawDownDuration(double maxDrawDownDuration) {
		this.maxDrawDownDuration = maxDrawDownDuration;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String toString(){
		return "final account info:\n"+finalAccount.accountInfo();
	}
}
