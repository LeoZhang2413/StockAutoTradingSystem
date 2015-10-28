package com.aeolus.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Logger;

import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.OriginalHistoricalDataManager;
import com.aeolus.resources.data.HistoricalDataRequest;
import com.aeolus.resources.data.Quote;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;
import com.ib.client.TagValue;

public class ReqHistoricalDataTask extends Task{
	private static Logger LOGGER = Logger.getLogger(ReqHistoricalDataTask.class.getName());
	private Vector<TagValue> m_mktDataOptions = new Vector<TagValue>();
	private Contract contract;
	private long endTime;
	private long startTime;
	private BarSize barSize;
	private String whatToShow = "TRADES";
	private int regularTradingHours = 1;// 0 or 1
	private int dataFormatStyle = 1; //1 or 2
	private TreeMap<Date,Quote> resultQuotes = new TreeMap<Date,Quote>();
	public ReqHistoricalDataTask(SystemBase base, Contract contract, String startTime, String endTime, BarSize barSize){
		super(base);
		this.type = TaskType.REQUEST_HISTORY_DATA;
		this.contract = contract;
		this.endTime = MyUtil.toDate(endTime).getTime();
		this.startTime = MyUtil.toDate(startTime).getTime();
		this.barSize = barSize;
	}
	public void processingData(String date, double open, double high, double low, double close, double volume,
			int count, double WAP, boolean hasGaps){
		if(date.startsWith("finished")){
			requestData();
			return;
		}
		Quote currentQuote = new Quote(MyUtil.toDate(date), open, close, high, low, volume);
		resultQuotes.put(currentQuote.getTime(),currentQuote);
	}
	
	/**
	 * @return return false if no further request is needed
	 */
	public void requestData(){
		long currentEndTime = resultQuotes.isEmpty()?endTime:resultQuotes.firstKey().getTime();
		long entireDuration = currentEndTime - startTime;
		if(entireDuration<=0){
			finishTask();
			return;
		}else{
			long[] validDurations = barSize.validDurationInMils();
			//let currentDuration be the largest duration in valid duration array
			long currentDuration = validDurations[validDurations.length-1]; 
			for(int i=validDurations.length-1;i>=0;i--){
				if(validDurations[i]>entireDuration){
					currentDuration = validDurations[i];
				}else{
					break;
				}
			}
			LOGGER.info("fetching "+contract.m_symbol+"'s historical data ended at "+MyUtil.timeToStringWithTimeZone(currentEndTime)+" with a duration of "+BarSize.getDurationExpression(currentDuration));
			int reqId = SystemCore.nextAvailableId();
			registerId(reqId);
			new HistoricalDataRequest(this.client).sendRequest(reqId, contract, MyUtil.timeToStringWithTimeZone(currentEndTime), BarSize.getDurationExpression(currentDuration), barSize.getValue(), whatToShow, regularTradingHours, dataFormatStyle, m_mktDataOptions);
		}
	}
	@Override
	public void startTask() {
		requestData();
	}
	@Override
	public void specialFinish() {
		OriginalHistoricalDataManager.getOriginalHistoricalData(contract).appendData(barSize, resultQuotes);
		OriginalHistoricalDataManager.getOriginalHistoricalData(contract).writeToDisk();
	}
	@Override
	public void onError(){
		finishTask();
	}
}
