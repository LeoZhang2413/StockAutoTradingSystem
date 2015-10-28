package com.aeolus.resources.manager;

import java.util.Date;
import java.util.TreeMap;

import com.aeolus.constant.BarSize;
import com.aeolus.core.SystemBase;
import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.data.AdjustedHistoricalDataManager;
import com.aeolus.resources.data.HistoricalData;
import com.aeolus.resources.data.OriginalHistoricalData;
import com.aeolus.resources.data.OriginalHistoricalDataManager;
import com.aeolus.resources.data.Quote;
import com.aeolus.util.ContractFactory;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public class ResourceManager {
	private static StockChart chart = new StockChart();
	private static SystemBase core = new SystemBase();
	public static OriginalHistoricalData getOriginalHistoricalData(Contract contract){
		return OriginalHistoricalDataManager.getOriginalHistoricalData(contract);
	}
	public static void loadHistoricalDataFromDisk(){
		OriginalHistoricalDataManager.loadFromDisk();
	}
	public static StockChart getStockChart(){
		return chart;
	}
	public static void connectToServer(){
		core.connect();
	}
	public static void downloadHistoricalData(String symbol,Date startTime, Date endTime, BarSize barSize){
		core.RequestHistoricalData(ContractFactory.stockContract(symbol), MyUtil.timeToString(startTime.getTime()), MyUtil.timeToString(endTime.getTime()), barSize);
	}
	public static void getAdjustedRatio(Contract contract){
		OriginalHistoricalDataManager.getAdjustedRatio(contract);
	}
	public static TreeMap<Date,Quote> getHistoricalData(Contract contract, BarSize barSize, boolean adjusted){
		TreeMap<Date,Quote> quoteMap = null;
		if(adjusted){
			quoteMap = AdjustedHistoricalDataManager.getAdjustedHistoricalQuotes(contract,barSize);
		}else{
			quoteMap = OriginalHistoricalDataManager.getOriginalHistoricalQuotes(contract, barSize);
		}
		return quoteMap;
	}
}
