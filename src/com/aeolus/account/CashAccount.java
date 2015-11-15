package com.aeolus.account;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.aeolus.strategy.TradingSignal;
import com.aeolus.util.MyUtil;

public class CashAccount extends Account{
	private double initialNetworth;
	public double getInitialNetworth() {
		return initialNetworth;
	}
	public double getCash() {
		return cash;
	}
	private double cash;
	private double tradingFeePerShare = 0.005;//interactive broker fee for US stocks
	public CashAccount(double initialCash, double tradingFeePerShare){
		this(initialCash);
		this.tradingFeePerShare = tradingFeePerShare;
	}
	public CashAccount(double initialCash){
		cash = initialCash;
		initialNetworth = initialCash;
	}
	public double currentNetworth(){
		return cash + positionValue();
	}
	public double positionValue(){
		double sum =0;
		for(Position position:positionMap.values()){
			if(position.getPositionType().equals(PositionType.LONG)){
				Double currentPrice = priceGroup.getPrice(position.getContractID());
				if(currentPrice == null){
					throw new PositionOperationException(position.getContractID()+"'s price is unknown, cannot calculate position price");
				}else{
					sum+=currentPrice*position.getPosition();
				}
			}else{
				throw new PositionOperationException("short position error! cash account can only have long position");
			}
		}
		return sum;
	}
	@Override
	public int openLongPosition(String contractID, double price, int amount) throws PositionOperationException{
		if(amount<=0){
			throw new PositionOperationException("amount negative");
		}
		if((price+tradingFeePerShare)*amount>cash){
			amount = (int) (cash/(price+tradingFeePerShare));
		}
		if(floorToHundredShares){
			amount = (amount/100)*100;
		}
		if(!positionMap.containsKey(contractID)){
			positionMap.put(contractID, new Position(contractID,PositionType.LONG));
		}
		Position currentPosition = positionMap.get(contractID);
		if(currentPosition.getPositionType().equals(PositionType.LONG)){
			cash-=(price+tradingFeePerShare)*amount;
			tradingFeeSum += tradingFeePerShare*amount;
			currentPosition.addLongPosition(amount, price);
			return amount;
		}else{
			throw new PositionOperationException("existing position is not a long position, cannot open long position until you close all short positions");
		}
	}
	@Override
	public int closeLongPosition(String contractID, double price, int amount) throws PositionOperationException{
		if(amount<=0){
			throw new PositionOperationException("amount negative"); 
		}
		Position currentPosition = positionMap.get(contractID);
		if(currentPosition!=null&&currentPosition.getPositionType().equals(PositionType.LONG)){
			int acutualAmount = currentPosition.substractLongPosition(amount, price);
			cash+=acutualAmount*price;
			cash-=tradingFeePerShare*acutualAmount;
			tradingFeeSum += tradingFeePerShare*acutualAmount;
			return acutualAmount;
		}else{
			throw new PositionOperationException("position not exist, or the existing position is a short positioin");
		}
	}
	@Override
	public String accountInfo() {
		DecimalFormat formatter = new DecimalFormat("0.00");
		return "initial networth: "+formatter.format(initialNetworth)+"\ncurrent networth: "+formatter.format(currentNetworth())+"\nfee: "+formatter.format(tradingFeeSum)+"\ncash: "+formatter.format(cash)+"\nposition value: "+positionValue()+"\n"+positionInfo();
	}
	@Override
	public void excuteSignal(TradingSignal signal) {
		switch(signal.getType()){
			case BUY: {
				openLongPosition(MyUtil.ContractIdentifier(signal.getContract()),signal.getPrice(),1000000);
				break;
			}
			case SELL: {
				closeLongPosition(MyUtil.ContractIdentifier(signal.getContract()),signal.getPrice(),1000000);
				break;
			}
			default:{
				
			}
		}
	}
}
