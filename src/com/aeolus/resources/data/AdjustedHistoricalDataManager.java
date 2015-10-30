package com.aeolus.resources.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.TreeMap;

import com.aeolus.constant.BarSize;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public class AdjustedHistoricalDataManager {
	private static Logger LOGGER = Logger.getLogger(AdjustedHistoricalDataManager.class.getName());
	private static Map<String,HistoricalData> adjustedHistoricalCache= new Hashtable<String,HistoricalData>();
	public static TreeMap<Date,Quote> getAdjustedHistoricalQuotes(Contract contract,BarSize barSize){
		if(adjustedHistoricalCache.get(MyUtil.ContractIdentifier(contract))==null){
			adjustedHistoricalCache.put(MyUtil.ContractIdentifier(contract),new AdjustedHistoricalData(contract));
		}
		TreeMap<Date,Quote> adjustedMap = adjustedHistoricalCache.get(MyUtil.ContractIdentifier(contract)).getQuotes(barSize);
		TreeMap<Date,Quote> originMap = OriginalHistoricalDataManager.getOriginalHistoricalData(contract).getQuotes(barSize);
		if(originMap.isEmpty() || (!adjustedMap.isEmpty() && adjustedMap.firstKey().equals(originMap.firstEntry())&& adjustedMap.lastKey().equals(originMap.lastKey()))){
			//does not need computation, just return adjustedMap
			return adjustedMap;
		}else{
			return calculateAndStoreAdjustedHistoricalQuotes(contract, barSize);
		}
	}
	private AdjustedHistoricalDataManager(){};
	/*public static AdjustedHistoricalData getAdjustedHistoricalData(Contract contract){
		if(contract)
	}*/
	private static TreeMap<Date,Quote> calculateAndStoreAdjustedHistoricalQuotes(Contract contract, BarSize barSize){
		TreeMap<Date,Quote> adjustedMap = adjustedHistoricalCache.get(MyUtil.ContractIdentifier(contract)).getQuotes(barSize);
		OriginalHistoricalData originalHistoricalData = OriginalHistoricalDataManager.getOriginalHistoricalData(contract);
		TreeMap<Date,QuoteGroup> originCompactMap = getCompactData(originalHistoricalData.getQuotes(barSize));
		TreeMap<Date,Double> adjustedCloseMap = originalHistoricalData.getAdjustedClose();
		Iterator<Entry<Date,QuoteGroup>> originCompactIterator = originCompactMap.entrySet().iterator();
		Iterator<Entry<Date,Double>> adjustedCloseIterator = adjustedCloseMap.entrySet().iterator();
		if(!adjustedCloseIterator.hasNext()){
			LOGGER.warning("please download adjusted close price data from yahoo first before calculating adjusted historical quotes");
			return adjustedMap;
		}
		if(!originCompactIterator.hasNext()){
			LOGGER.warning("the original historical data is empty, cannot calculate adjusted historical quotes.");
			return adjustedMap;
		}
		Entry<Date,QuoteGroup> currentCompactQuoteEntry = originCompactIterator.next();
		Entry<Date,Double> currentAdjustedCloseEntry = adjustedCloseIterator.next();
		while(true){
			Date quoteDate = MyUtil.getDayMidnight(currentCompactQuoteEntry.getKey());
			Date adjustedCloseDate = currentAdjustedCloseEntry.getKey();
			if(quoteDate.equals(adjustedCloseDate)){
				QuoteGroup currentCompactQuote = currentCompactQuoteEntry.getValue();
				Double ratio = currentAdjustedCloseEntry.getValue()/currentCompactQuote.getClose();
				for(Quote quote : currentCompactQuote.getQuotes()){
					adjustedMap.put(quote.getTime(), new Quote(quote.getTime(), quote.getOpen()*ratio, quote.getClose()*ratio, quote.getHigh()*ratio, quote.getLow()*ratio, quote.getVolume()/ratio));	
				}
				if(!originCompactIterator.hasNext() || !adjustedCloseIterator.hasNext()) break;
				currentCompactQuoteEntry = originCompactIterator.next();
				currentAdjustedCloseEntry = adjustedCloseIterator.next();
			}else if(quoteDate.before(adjustedCloseDate)){
				if(!originCompactIterator.hasNext()) break;
				currentCompactQuoteEntry  = originCompactIterator.next();
			}else{
				if(!adjustedCloseIterator.hasNext()) break;
				currentAdjustedCloseEntry = adjustedCloseIterator.next();
			}
		}
		return adjustedMap;
	}
	public static TreeMap<Date,QuoteGroup> getCompactData(TreeMap<Date,Quote> originalData){
		TreeMap<Date,QuoteGroup> groupTree = new TreeMap<Date,QuoteGroup>();
		QuoteGroup currentGroup = new QuoteGroup();
		for(Quote quote:originalData.values()){
			if(!currentGroup.addQuote(quote)){
				groupTree.put(currentGroup.getDateInMidnight(), currentGroup);
				currentGroup = new QuoteGroup();
				currentGroup.addQuote(quote);
			}
		}
		if(currentGroup.isEmpty()){
			groupTree.put(currentGroup.getDateInMidnight(), currentGroup);
		}
		return groupTree;
	}
}
