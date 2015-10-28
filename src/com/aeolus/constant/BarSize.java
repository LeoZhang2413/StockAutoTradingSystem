package com.aeolus.constant;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import com.aeolus.core.ReqHistoricalDataTask;
//should be rewritten, some codes are poor
public enum BarSize {
	Second1("1 secs",getLength("1 S")),Second5("5 secs",getLength("5 S")),Second10("10 secs",getLength("10 S")),Second15("15 secs",getLength("15 S")),Second30("30 secs",getLength("30 S")),
	Minute1("1 min",getLength("60 S")),Minute2("2 mins",getLength("120 S")),Minute3("3 mins",getLength("180 S")),Minute5("5 mins",getLength("300 S")),Minute10("10 mins",getLength("600 S")),
	Minute15("15 mins",getLength("900 S")),Minute20("20 mins",getLength("1200 S")),Minute30("30 mins",getLength("1800 S")),
	Hour1("1 hour",getLength("3600 S")),Hour2("2 hours",getLength("7200 S")),Hour3("3 hours",getLength("10800 S")),Hour4("4 hours",getLength("14400 S")),Hour8("8 hours",getLength("28800 S")),Day1("1 day",getLength("1 D")),Week1("1 W",getLength("1 W")),Month1("1 M",getLength("1 M"));
	private static Map<BarSize,Integer> maxDurationIndexMap = new Hashtable<BarSize,Integer>();
	private static Map<BarSize,Integer> minDurationIndexMap = new Hashtable<BarSize,Integer>();
	private static long[] duration;
	private static Map<Long,String> durationMap = new Hashtable<Long,String>();
	private static Logger LOGGER = Logger.getLogger(BarSize.class.getName());
	static{
		Integer[] maxIndex = {7,9,11,11,12,13,14,15,15,15,16,16,17,17,17,17,17,17,21,21,21};
		Integer[] minIndex = {0,0,0,0,0,0,1,2,3,4,5,6,7,8,9,10,12,12,13,15,17};
		for(int i=0;i<BarSize.values().length;i++){
			maxDurationIndexMap.put(BarSize.values()[i], maxIndex[i]);
			minDurationIndexMap.put(BarSize.values()[i], minIndex[i]);
		}
		String[] durationExp = {"60 S", "120 S","180 S","300 S","600 S","900 S","1200 S","1800 S","3600 S","7200 S","10800 S","14400 S","28800 S","1 D","2 D","1 W","2 W","1 M","3 M","6 M","1 Y","5 Y"};
		duration = new long[durationExp.length];
		for(int i=0;i<duration.length;i++){
			duration[i] = getLength(durationExp[i]);
		}
		for(int i=0;i<duration.length;i++){
			durationMap.put(duration[i], durationExp[i]);
		}
	}
	private static long getLength(String str){
		String[] exps = str.split(" ");
		long time = 0;
		switch(exps[1]){
			case "S":{
				time = Long.parseLong(exps[0]) *1000;
				break;
			}
			case "D":{
				time = Long.parseLong(exps[0]) *1000*3600*24;
				break;
			}
			case "W":{
				time = Long.parseLong(exps[0]) *1000*3600*24*7;
				break;
			}
			case "M":{
				time = Long.parseLong(exps[0]) *1000*3600*24*30;
				break;
			}
			case "Y":{
				time = Long.parseLong(exps[0]) *1000*3600*24*365;
				break;
			}
			default:{
				LOGGER.severe("format error, should end with S, D, W, M or Y");
			}
		}
		return time;
	}
	private final String value;
	private final long lengthInMills;
	BarSize(String value,long lengthInMills){
		this.value = value;
		this.lengthInMills = lengthInMills;
	}
	public String getValue(){
		return value;
	}
	public long getLengthInMills(){
		return lengthInMills;
	}
	public static String getDurationExpression(long duration){
		return durationMap.get(duration);
	}
	public long[] validDurationInMils(){
		return Arrays.copyOfRange(duration, minDurationIndexMap.get(this), maxDurationIndexMap.get(this)+1);
	}
}
