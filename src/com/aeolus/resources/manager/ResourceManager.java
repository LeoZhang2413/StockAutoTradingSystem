package com.aeolus.resources.manager;

import java.util.Date;
import java.util.TreeMap;

import com.aeolus.constant.BarSize;
import com.aeolus.core.SystemBase;
import com.aeolus.resources.chart.BackTestingStockChart;
import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.data.AdjustedHistoricalDataManager;
import com.aeolus.resources.data.HistoricalData;
import com.aeolus.resources.data.OriginalHistoricalData;
import com.aeolus.resources.data.OriginalHistoricalDataManager;
import com.aeolus.resources.data.Quote;
import com.aeolus.resources.data.YahooDataFetcher;
import com.aeolus.swinggui.BacktestingWindow;
import com.aeolus.swinggui.ControlPanel;
import com.aeolus.swinggui.HistoricalDataWindow;
import com.aeolus.swinggui.InfoWindowModel;
import com.aeolus.swinggui.InforWindow;
import com.aeolus.util.ContractFactory;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public class ResourceManager {
	private static StockChart stockChart = new StockChart();
	private static BackTestingStockChart backTestingChart = new BackTestingStockChart();
	private static SystemBase core = new SystemBase();
	private static HistoricalDataWindow historicalDataWindow_;
	private static BacktestingWindow backtestingWindow_;
	private static boolean downloading = false;
	private static boolean connected = false;
	public static BackTestingStockChart getBackTestingChart() {
		return backTestingChart;
	}
	public static boolean isConnected() {
		return connected;
	}
	public static void setConnected(boolean connected) {
		ResourceManager.connected = connected;
	}
	public static boolean isDownloading() {
		return downloading;
	}
	public static void setDownloading(boolean downloading) {
		ResourceManager.downloading = downloading;
	}
	public static void loadHistoricalDataFromDisk(){
		OriginalHistoricalDataManager.loadFromDisk();
	}
	public static StockChart getStockChart(){
		return stockChart;
	}
	public static void connectToServer(){
		core.connect();
	}
	/*public static void downloadHistoricalData(Contract contract,Date startTime, Date endTime, BarSize barSize){
		core.RequestHistoricalData(contract, MyUtil.timeToString(startTime.getTime()), MyUtil.timeToString(endTime.getTime()), barSize);
	}*/
	public static void downloadHistoricalDataAlongWithYahooAdjustedData(Contract contract,Date startTime, Date endTime,BarSize barSize){
		core.RequestHistoricalData(contract, MyUtil.timeToString(startTime.getTime()), MyUtil.timeToString(endTime.getTime()), barSize);
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
	public static void registerMainWindow(HistoricalDataWindow historicalDataWindow){
		historicalDataWindow_ = historicalDataWindow;
	}
	public static void setInfoWindowModel(Date date,double open, double close, double high, double low, long volume){
		historicalDataWindow_.getInfoWindow().setQuote(MyUtil.timeToString(date.getTime()),MyUtil.formatDouble(open),MyUtil.formatDouble(close),MyUtil.formatDouble(high),MyUtil.formatDouble(low),MyUtil.formatLong(volume));
	}
	public static void setDownloadingProcessBarValue(int value){
		historicalDataWindow_.getControlPanel().setProcessBarValue(value);
	}
	public static void registerBacktestingWindow(BacktestingWindow backtestingWindow){
		backtestingWindow_ = backtestingWindow;
	}
	public static BacktestingWindow getBacktestingWindow() {
		return backtestingWindow_;
	}
	public static void writeHistoricalDataToDisk(){
		OriginalHistoricalDataManager.writeHistoricalDataToDisk();
	}
}
