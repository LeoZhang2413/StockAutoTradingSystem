package com.aeolus.account;

import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import com.aeolus.resources.data.Quote;
import com.aeolus.strategy.TradingSignal;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

abstract public class Account{
	protected Map<String,Position> positionMap = new Hashtable<String,Position>();
	protected PositionPriceGroup priceGroup = new PositionPriceGroup();
	protected double tradingFeeSum;
	abstract public int openLongPosition(String contractID, double price, int amount);
	abstract public int closeLongPosition(String contractID, double price, int amount);
	abstract public String accountInfo();
	protected boolean floorToHundredShares = false;
	protected String positionInfo(){
		StringBuffer stringBuffer = new StringBuffer();
		for(Position position:positionMap.values()){
			stringBuffer.append(position.toString()+"\n");
		}
		return stringBuffer.toString();
	}
	public void updatePositionPrice(String contractID,double price){
		priceGroup.updatePrice(contractID, price);
	}
	public void updatePositionPrice(Map<Contract,Quote> quoteMap){
		for(Entry<Contract,Quote> entry:quoteMap.entrySet()){
			updatePositionPrice(MyUtil.ContractIdentifier(entry.getKey()),entry.getValue().getClose());
		}
	}
	abstract public void excuteSignal(TradingSignal signal);
}
