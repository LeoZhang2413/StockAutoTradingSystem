package com.aeolus.backtesting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.aeolus.account.Account;
import com.aeolus.strategy.Strategy;
import com.aeolus.strategy.TradingSignal;

public class BacktestReport {
	private double annualSharpeRatio;
	private double maxDrawDown;
	private double maxDrawDownDuration;
	private Date startDate;
	private Date endDate;
	private Account account;
	//private List<TradingSignal> tradingHistory = new ArrayList<TradingSignal>();
	private TreeMap<Date,Double> networth = new TreeMap<Date,Double>();
	public BacktestReport(Strategy strategy) {
		this.account = strategy.getAccount();
		this.startDate = strategy.getStartDate();
		this.endDate = strategy.getEndDate();
	}
	/*public void recordSignal(TradingSignal signal){
		tradingHistory.add(signal);
	}
	public List<TradingSignal> getTradingHistory(){
		return tradingHistory;
	}*/

	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
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
		return "final account info:\n"+account.accountInfo();
	}
	protected void recordNetworth(Date date, Double value){
		networth.put(date, value);
	}
	public TreeMap<Date,Double> getNetworth(){
		return networth;
	}
}
