package com.aeolus.util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.DateTime;

import com.ib.client.Contract;
public class MyUtil {
	private static SimpleDateFormat endDateFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss z",Locale.US);
	private static SimpleDateFormat fullDateFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss",Locale.US);
	private static SimpleDateFormat halfDateFormatter = new SimpleDateFormat("yyyyMMdd",Locale.US);
	private static DecimalFormat doubleFormat = new DecimalFormat("#.##");
	private static DecimalFormat longFormat = new DecimalFormat("#");
	static{
		endDateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	public static String currentTime(){
		return endDateFormatter.format(new Date());
	}
	public static String ContractIdentifier(Contract contract){
		return contract.m_symbol+"."+contract.m_secType;
	}
	public static Date toDate(String timeStamp){
		try {
			if(timeStamp.length()==8){
				return halfDateFormatter.parse(timeStamp);
			}else{
				return fullDateFormatter.parse(timeStamp);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static String timeToString(long time){
		return fullDateFormatter.format(new Date(time));
	}
	public static String timeToStringWithTimeZone(long time){
		return endDateFormatter.format(time);
	}
	private static long secondsFromNow(String timeStamp){
		Date date = toDate(timeStamp);
		return (new Date().getTime()-date.getTime())/1000;
	}
	public static String getDurationFromNow(String timeStamp){
		long secondsFromNow = secondsFromNow(timeStamp);
		if(secondsFromNow<86400){
			return secondsFromNow+" S";
		}else{
			long days = secondsFromNow/86400+1;
			if(days<=30){
				return days+" D";
			}else{
				long months = days/30 +1;
				if(months<=12){
					return months + " M";
				}else{
					long years = days/365 +1;
					return (years>5?5:years) + " Y";
				}
			}
		}
	}
	public static void createFolder(String folderName){
		File directory = new File(folderName);
		if(!directory.exists()||directory.isFile()){
			directory.mkdirs();
		}
	}
	public static Date dateTimeToString(DateTime dateTime){
		String year = String.valueOf(dateTime.getYear());
		String month = String.valueOf(dateTime.getMonth()+1);
		if(month.length()==1){
			month = "0" + month;
		}
		String day = String.valueOf(dateTime.getDay());
		if(day.length()==1){
			day = "0" + day;
		}
		return toDate(year+month+day);
	}
	// 20140502 13:00:22 --> 20140502 00:00:00
	public static Date getDayMidnight(Date date){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static String formatDouble(double value){
		return doubleFormat.format(value);
	}
	
	public static String formatLong(long value){
		return longFormat.format(value);
	}
}
