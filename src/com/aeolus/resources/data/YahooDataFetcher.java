package com.aeolus.resources.data;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.aeolus.core.ReqHistoricalDataTask;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class YahooDataFetcher {
	private static Logger LOGGER = Logger.getLogger(YahooDataFetcher.class.getName());
	public static TreeMap<Date,Double> getAdjustedClose(Contract contract){
		Stock stock;
		TreeMap<Date,Double> result = new TreeMap<Date,Double>();
		if(contract.m_secType!="STK"){
			LOGGER.warning("cannot fetch non stock data");
			return result;
		}
		try {
			stock = YahooFinance.get(contract.m_symbol);
			Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
			startCal.setTime(new Date(0));
			for(HistoricalQuote quote:stock.getHistory(startCal,Interval.DAILY)){
				result.put(quote.getDate().getTime(), quote.getAdjClose().doubleValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
