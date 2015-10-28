package com.aeolus.resources.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import com.aeolus.constant.BarSize;
import com.aeolus.constant.SystemParams;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public abstract class HistoricalData implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String identifier;
	private Map<BarSize,TreeMap<Date,Quote>> data = new Hashtable<BarSize,TreeMap<Date,Quote>>();
	protected HistoricalData(Contract contract){
		this.identifier = MyUtil.ContractIdentifier(contract);
	}
	public synchronized TreeMap<Date,Quote> getQuotes(BarSize size){
		if(!data.containsKey(size)){
			data.put(size, new TreeMap<Date,Quote>());
		}
		return data.get(size);
	}
	public synchronized Quote getQuote(BarSize size, int reversedIndex){
		TreeMap<Date,Quote> quoteMap = getQuotes(size);
		if(quoteMap.size()==0){
			return null;
		}else{
			return quoteMap.lastEntry().getValue();
		}
	}
	public synchronized void appendData(BarSize size, TreeMap<Date,Quote> newQuotes){
		TreeMap<Date,Quote> quoteMap = getQuotes(size);
		quoteMap.putAll(newQuotes);
	}
}
