package com.aeolus.resources.data;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import com.aeolus.constant.BarSize;
import com.aeolus.constant.SystemParams;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public class OriginalHistoricalDataManager{
	//cause the ib api overrides Contract's equal function without overriding the hashcode, so I will not use the Contract as the key of the hashtable
	private static Map<String,OriginalHistoricalData> dataTable= new Hashtable<String,OriginalHistoricalData>();
	public static OriginalHistoricalData getOriginalHistoricalData(Contract contract){
		String key = MyUtil.ContractIdentifier(contract);
		if(!dataTable.containsKey(key)){
			dataTable.put(key, new OriginalHistoricalData(contract));
		}
		return dataTable.get(key);
	}
	public static TreeMap<Date,Quote> getOriginalHistoricalQuotes(Contract contract, BarSize barSize){
		return OriginalHistoricalDataManager.getOriginalHistoricalData(contract).getQuotes(barSize);
	}
	public static void loadFromDisk(){
		File dataRoot = new File(SystemParams.dataRootDir);
		if(dataRoot.exists()){
			if(dataRoot.isDirectory()){
				for(String fileName:dataRoot.list()){
					dataTable.put(fileName, OriginalHistoricalData.loadFromDisk(fileName));
				}
			}
		}
	}
	public static void getAdjustedRatio(final Contract contract){
		new Thread(){public void run(){
				getOriginalHistoricalData(contract).addAdjustedCloseData(YahooDataFetcher.getAdjustedCloseRatio(contract));
			};
		}.start();
	}
}
