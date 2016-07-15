package com.aeolus.account;

import java.text.DecimalFormat;

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
	public int openLongPosition(String contractID, double price, int amount){
		if(amount<=0){
			return 0;
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
		}
		return 0;
	}
	@Override
	public int closeLongPosition(String contractID, double price, int amount){
		if(amount<=0){
			return 0;
		}
		Position currentPosition = positionMap.get(contractID);
		if(currentPosition!=null&&currentPosition.getPositionType().equals(PositionType.LONG)){
			int acutualAmount = currentPosition.substractLongPosition(amount, price);
			cash+=acutualAmount*price;
			cash-=tradingFeePerShare*acutualAmount;
			tradingFeeSum += tradingFeePerShare*acutualAmount;
			if(currentPosition.getPosition() == 0){
				positionMap.remove(contractID);
			}
			return acutualAmount;
		}
		return 0;
	}
	@Override
	public String accountInfo() {
		DecimalFormat formatter = new DecimalFormat("0.00");
		return "initial networth: "+formatter.format(initialNetworth)+"\ncurrent networth: "+formatter.format(currentNetworth())+"\nfee: "+formatter.format(tradingFeeSum)+"\ncash: "+formatter.format(cash)+"\nposition value: "+positionValue()+"\n"+positionInfo();
	}
	@Override
	public void excuteSignal(TradingSignal signal) {
		switch(signal.getType()){
			case BUY2OPEN: {
				int amount = openLongPosition(MyUtil.ContractIdentifier(signal.getContract()),signal.getPrice(),signal.getVolumn());
				if(amount!=0){
					TradingSignal tradingRecord = new TradingSignal(signal.getType(), signal.getContract(), signal.getTime(), amount, signal.getPrice());
					tradingHistory.add(tradingRecord);
				}
				break;
			}
			case SELL2CLOSE: {
				int amount = closeLongPosition(MyUtil.ContractIdentifier(signal.getContract()),signal.getPrice(),signal.getVolumn());
				if(amount!=0){
					TradingSignal tradingRecord = new TradingSignal(signal.getType(), signal.getContract(), signal.getTime(), amount, signal.getPrice());
					tradingHistory.add(tradingRecord);
				}
				break;
			}
			default:{
				
			}
		}
	}
}
