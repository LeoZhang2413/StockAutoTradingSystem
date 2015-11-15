package com.aeolus.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.Quote;
import com.aeolus.resources.manager.ResourceManager;
import com.ib.client.Contract;

public abstract class Strategy {
	private static Logger LOGGER = Logger.getLogger(Strategy.class.getName());
	private List<List<Quote>> quoteList = new ArrayList<List<Quote>>();
	private List<Contract> contracts;
	private BarSize barSize;
	private int listLength;
	private int cursor = -1;
	private Date startDate;
	private Date endDate;
	public List<Contract> getContracts() {
		return contracts;
	}
	protected Strategy(){}
	protected void feedData(List<Contract> contracts, BarSize barSize){
		this.contracts = contracts;
		this.barSize = barSize;
		Set<Date> availableDate = new HashSet<Date>();
		List<Collection<Quote>> historicalDataList = new ArrayList<Collection<Quote>>();
		if(contracts.size()!=0){
			for(int i=0;i<contracts.size();i++){
				historicalDataList.add(ResourceManager.getHistoricalData(contracts.get(i), barSize, true).values());
			}
			for(int i=0;i<contracts.size();i++){
				Collection<Quote> historicalData = historicalDataList.get(i);
				if(i==0){
					for(Quote currentQuote:historicalData){
						availableDate.add(currentQuote.getTime());
					}
				}else{
					Set<Date> currentDateSet = new HashSet<Date>();
					for(Quote currentQuote:historicalData){
						if(availableDate.contains(currentQuote.getTime())){
							currentDateSet.add(currentQuote.getTime());
						}
					}
					availableDate = currentDateSet;
				}
			}
			for(int i=0;i<contracts.size();i++){
				List<Quote> currentList = new ArrayList<Quote>();
				Collection<Quote> historicalData = historicalDataList.get(i);
				for(Quote currentQuote:historicalData){
					if(availableDate.contains(currentQuote.getTime())){
						currentList.add(currentQuote);
					}
				}
				if(i==0 && currentList.size()>0){
					startDate = currentList.get(0).getTime();
					endDate = currentList.get(currentList.size()-1).getTime();
				}
				listLength = currentList.size();
				quoteList.add(currentList);
			}
		}else{
			LOGGER.warning("data feeding failed");
		}
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void printData(){
		for(int j=0;j<quoteList.get(0).size();j++){
			for(List<Quote> list:quoteList){
				System.out.print(list.get(j)+"    ");
			}
			System.out.println();
		}
	}
	public void registerRealtimeDataSubscriber(Contract... contract){
		
	}
	public boolean hasNextCursor(){
		if(cursor < listLength-1){
			return true;
		}else{
			return false;
		}
	}
	public void nextCursor() throws NoNextCursorException{
		if(cursor < listLength-1){
			cursor++;
		}else{
			throw new NoNextCursorException("cann't find next cursor");
		}
	}
	public boolean cursorValid(){
		return cursor>-1 && cursor < listLength;
	}
	public List<TradingSignal> checkSignal() throws CursorInvalidException{
		if(cursorValid()){
			return checkSignal(contracts,quoteList,cursor);
		}else{
			throw new CursorInvalidException("cursor invalid");
		}
	}
	abstract protected List<TradingSignal> checkSignal(List<Contract> contracts,List<List<Quote>> quoteList,int currentCursor);
	public Map<Contract,Quote> getQuoteAtCurrentCursor() throws CursorInvalidException{
		if(cursorValid()){
			Map<Contract,Quote> quoteMap = new Hashtable<Contract,Quote>();
			for(int i=0;i<contracts.size();i++){
				quoteMap.put(contracts.get(i), quoteList.get(i).get(cursor));
			}
			return quoteMap;
		}else{
			throw new CursorInvalidException("cursor invalid");
		}
	}
}
