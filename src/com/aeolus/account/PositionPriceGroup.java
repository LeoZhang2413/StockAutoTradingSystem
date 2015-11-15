package com.aeolus.account;

import java.util.Hashtable;
import java.util.Map;

public class PositionPriceGroup {
	private Map<String,Double> prices = new Hashtable<String,Double>();
	public void updatePrice(String contractID,double price){
		prices.put(contractID, price);
	}
	public Double getPrice(String contractID){
		return prices.get(contractID);
	}
}
