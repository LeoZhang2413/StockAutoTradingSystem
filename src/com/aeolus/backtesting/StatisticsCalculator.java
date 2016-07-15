package com.aeolus.backtesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.aeolus.constant.BarSize;
import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.util.ContractFactory;

import java.util.TimeZone;
import java.util.TreeMap;

public class StatisticsCalculator {
	protected static Logger LOGGER = Logger.getLogger(StatisticsCalculator.class.getName());
	private static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("American/New_York"));
	private static double TRADING_DAY = 252;
	/*
	private TreeMap<Date,Double> networth = new TreeMap<Date,Double>();
	public int monthlySharpeRatio(){
		Date a = new Date();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("American/New_York"));
		calendar.setTime(a);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}*/
	public static void main(String[] args){
		//System.out.println(Math.pow((1111052.62)/1002109.69,1.0/30));
		Double[] networth1 = {1002109.69,1002804.38,1002804.38,1002968.76,999500.95,1005498.14,1023029.42,1029213.2,1028899.47,1028899.47,1028899.47,1028263.28,1027107.05,1027358.82,1035969.58,1011775.84,1011775.84,1011775.84,1013882.12,1021886.38,1039668.71,1052326.54,1030042.65,1030042.65,1030042.65,1051649.98,1117159.01,1111144.16,1124070.89,1111052.62};
		List<Double> networth = Arrays.asList(networth1);
		StatisticsCalculator.sharpeRationFromDailyData(networth, 0.03);
		
	}
	public static double sharpeRationFromDailyData(List<Double> networth, double annualRiskFreeRatio){
		double dailyPortfolioReturnGeometricMean = Math.pow(networth.get(networth.size()-1)/networth.get(0), 1.0/(networth.size()-1))-1;
		double annualReturn = dailyPortfolioReturnGeometricMean * TRADING_DAY;
		//System.out.println(dailyPortfolioReturnGeometricMean);
		double[] dailyReturn = new double[networth.size()-1];
		StandardDeviation sd = new StandardDeviation(false);
		for(int i=1;i<networth.size();i++){
			dailyReturn[i-1]=(networth.get(i)-networth.get(i-1))/networth.get(i-1)-1;
		}
		double standardDeviation = sd.evaluate(dailyReturn);
		double standardDeviationAnnual = standardDeviation*Math.sqrt(TRADING_DAY);
		return (annualReturn - annualRiskFreeRatio)/standardDeviationAnnual;
	}
	public static double sharpeRatioFromDailyData(TreeMap<Date,Double> networthTree, double annualRiskFreeRatio){
		List<Double> networth = new ArrayList<Double>(networthTree.values());
		if(networth.size()>1){
			return sharpeRationFromDailyData(networth,annualRiskFreeRatio);
		}else{
			LOGGER.severe("networth length should be larger than 1");
			return 0;
		}
	}
	public static double informationRatioFromDailyData(TreeMap<Date,Double> networthTree, List<Double> benchMarkNetworth){
		List<Double> networth = new ArrayList<Double>(networthTree.values());
		if(networth.size() == benchMarkNetworth.size() && networth.size()>1){
			double dailyPortfolioReturnGeometricMean = Math.pow(networth.get(networth.size()-1)/networth.get(0), 1.0/(networth.size()-1))-1;
			double annualReturn = dailyPortfolioReturnGeometricMean * TRADING_DAY;
			double dailyBenchmarkReturnGeometricMean = Math.pow((benchMarkNetworth.get(networth.size()-1)-benchMarkNetworth.get(0))/benchMarkNetworth.get(0), benchMarkNetworth.size()-1)-1;
			double annualBenchmarkReturn = dailyBenchmarkReturnGeometricMean * TRADING_DAY;
			double[] dailyExcessReturn = new double[networth.size()-1];
			StandardDeviation sd = new StandardDeviation(false);
			for(int i=1;i<networth.size();i++){
				dailyExcessReturn[i-1]=((networth.get(i)-networth.get(i-1))/networth.get(i-1) - (benchMarkNetworth.get(i)-benchMarkNetworth.get(i-1))/benchMarkNetworth.get(i-1));
			}
			double standardDeviation = sd.evaluate(dailyExcessReturn);
			double standardDeviationAnnual = standardDeviation*Math.sqrt(TRADING_DAY);
			return (annualReturn - annualBenchmarkReturn)/standardDeviationAnnual;
		}else{
			LOGGER.severe("networth length is different from benchmark length");
			return 0;
		}
	}
	private static TreeMap<Date,Double> getDailyNetworthListFromLowerLevelBarSize(TreeMap<Date,Double> networth){
		List<List<Entry<Date,Double>>> recordGroupList = new ArrayList<List<Entry<Date,Double>>>();
		List<Entry<Date,Double>> recordGroup = new ArrayList<Entry<Date,Double>>();
		TreeMap<Date,Double> networthTree = new TreeMap<Date,Double>();
		for(Entry<Date,Double> entry:networth.entrySet()){
			if(!recordGroup.isEmpty() && !isOfTheSameDay(entry.getKey(),recordGroup.get(0).getKey())){
				recordGroupList.add(recordGroup);
				recordGroup = new ArrayList<Entry<Date,Double>>();
			}
			recordGroup.add(entry);
		}
		if(!recordGroup.isEmpty()){
			recordGroupList.add(recordGroup);
		}
		for(List<Entry<Date,Double>> entryList:recordGroupList){
			Entry<Date,Double> lastRecord = entryList.get(entryList.size()-1);
			networthTree.put(lastRecord.getKey(),lastRecord.getValue());
		}
		return networthTree;
	}
	private static boolean isOfTheSameDay(Date date1, Date date2){
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);
		return year1 == year2 && month1 == month2 && day1 == day2;
	}
}
