package com.aeolus.resources.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aeolus.util.MyUtil;

public class QuoteGroup{
	private Date dateInMidnight;
	private List<Quote> quotesInADay = new ArrayList<Quote>();
	private double close;
	public boolean addQuote(Quote quote){
		Date targetDate = MyUtil.getDayMidnight(quote.getTime());
		if(quotesInADay.isEmpty()){
			dateInMidnight = targetDate;
		}else{
			if(!targetDate.equals(dateInMidnight)){
				return false;
			}
		}
		quotesInADay.add(quote);
		close = quote.getClose();
		return true;
	}
	public double getClose() {
		return close;
	}
	public List<Quote> getQuotes(){
		return quotesInADay; 
	}
	public Date getDateInMidnight(){
		return dateInMidnight;
	}
	public boolean isEmpty(){
		return quotesInADay.isEmpty();
	}
}