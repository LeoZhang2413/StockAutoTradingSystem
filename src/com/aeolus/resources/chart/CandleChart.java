package com.aeolus.resources.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;

import com.aeolus.backtesting.BacktestReport;
import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.Quote;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.strategy.SignalType;
import com.aeolus.strategy.TradingSignal;
import com.aeolus.util.ChartHelper;
import com.aeolus.util.ContractFactory;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public abstract class CandleChart {
	protected static Logger LOGGER = Logger.getLogger(StockChart.class.getName());
	XYPlot candleChart;
	private ValueMarker marker;
	private Date currentMarkerPosition = new Date(0);
	Contract currentContract;
	BarSize currentBarsize;
	private boolean adjusted = false;
	CandlestickRenderer renderer;
	protected DateAxis dateAxis;
	protected NumberAxis priceAxis;
	List<OHLCDataItem> existDataset = new ArrayList<OHLCDataItem>();
	private Map<Date,TradingSignal> tradingHistoryMap = new HashMap<Date,TradingSignal>();
	public CandleChart(){
		dateAxis = new DateAxis("Date");
		currentBarsize = BarSize.Day1;
		dateAxis.setTimeline(ChartHelper.getTimeline(currentBarsize));
		priceAxis = new NumberAxis("Price");
		renderer = new CandlestickRenderer();
		renderer.setSeriesPaint(0, Color.BLACK);
		DefaultOHLCDataset dataset = getEmptyDataSet();
		candleChart = new XYPlot(dataset,dateAxis, priceAxis, renderer);
		priceAxis.setAutoRangeIncludesZero(false);
	}
	public XYPlot getXYPlot(){
		return candleChart;
	}
	private void setDashMarker(Date date){
		if(marker!=null){
			marker.setValue(date.getTime());
			currentMarkerPosition = date;
			onDashMarkerSet();
			//updateInformationWindow();
			return;
		}
		marker = new ValueMarker(date.getTime());  // position is the value on the axis
		marker.setPaint(Color.blue);
		marker.setStroke(new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
            1.0f, new float[] {10.0f, 6.0f}, 0.0f));
		candleChart.addDomainMarker(marker);
		currentMarkerPosition = date;
		onDashMarkerSet();
		//updateInformationWindow();
	}
	public void MoveDashMarker(boolean right){
		Range range = dateAxis.getRange();
		long rangeEnd = (long)range.getUpperBound();
		long rangeStart = (long)range.getLowerBound();
		TreeMap<Date,Quote> quoteMap = ResourceManager.getHistoricalData(currentContract, currentBarsize, adjusted);
		SortedMap<Date,Quote> subMap = quoteMap.subMap(new Date(rangeStart), new Date(rangeEnd+1));
		if(currentMarkerPosition.before(subMap.firstKey())||currentMarkerPosition.after(subMap.lastKey())){
			if(right){ setDashMarker(subMap.firstKey());}
			else{ setDashMarker(subMap.lastKey());}
		}else{
			if(right){
				if(currentMarkerPosition.before(quoteMap.lastKey())){
					setDashMarker(quoteMap.higherKey(currentMarkerPosition));
					if(currentMarkerPosition.after(subMap.lastKey())){
						setDateRange(quoteMap.higherKey(subMap.firstKey()),currentMarkerPosition);
						adjustValueRange();
					}
				}
			}else{
				if(currentMarkerPosition.after(quoteMap.firstKey())){
					setDashMarker(quoteMap.lowerKey(currentMarkerPosition));
					if(currentMarkerPosition.before(subMap.firstKey())){
						setDateRange(currentMarkerPosition,quoteMap.lowerKey(subMap.lastKey()));
						adjustValueRange();
					}
				}
			}
		}
	}
	public Date getCurrentMarkerPosition(){
		if(currentMarkerPosition == null){
			LOGGER.warning("marker position is not set yet");
		}
		return currentMarkerPosition;
	}
	/*public StockChart(Contract contract){
		currentContract = contract;
		currentBarsize = BarSize.Day1;
		dateAxis = new DateAxis("Date");
		dateAxis.setTimeline(ChartHelper.getTimeline(BarSize.Day1));
		NumberAxis priceAxis = new NumberAxis("Price");
		CandlestickRenderer renderer = new CandlestickRenderer();
		renderer.setSeriesPaint(0, Color.BLACK);
		DefaultOHLCDataset dataset = getDataSet(contract,BarSize.Day1,adjusted);
		XYPlot mainPlot = new XYPlot(dataset,dateAxis, priceAxis, renderer); 
		priceAxis.setAutoRangeIncludesZero(false);
		JFreeChart chart = new JFreeChart(MyUtil.ContractIdentifier(contract),null,mainPlot,false);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1000,500));
	}*/
	public void changeContent(Contract contract,BarSize barSize){
		currentContract = contract;
		currentBarsize = barSize;
		//renderer.setCandleWidth(10);
		//renderer.setAutoWidthGap(0.1);
		renderer.setAutoWidthFactor(1000);
		renderer.setMaxCandleWidthInMilliseconds(barSize.getLengthInMills()*0.8);
		dateAxis.setTimeline(ChartHelper.getTimeline(barSize));
		setDataset(getDataSet(contract,barSize,adjusted));
		initialAxisRange();
	}
	private void initialAxisRange(){
		TreeMap<Date,Quote> quoteMap = ResourceManager.getHistoricalData(currentContract, currentBarsize, adjusted);
		if(!quoteMap.isEmpty()){
			setDateRange(quoteMap.firstKey(),new Date(quoteMap.lastKey().getTime()+1));
			adjustValueRange();
		}
	}
	public void setDateRange(Date from, Date to){
		dateAxis.setRange(from, to);
	}
	public void adjustValueRange(){
		Range range = dateAxis.getRange();
		long end = (long)range.getUpperBound();
		long start = (long)range.getLowerBound();
		if(currentContract!=null){
			double highestPrice = Double.MIN_VALUE;
			double lowestPrice = Double.MAX_VALUE;
			TreeMap<Date,Quote> quoteMap = ResourceManager.getHistoricalData(currentContract, currentBarsize, adjusted);
			if(quoteMap.isEmpty()){
				return;
			}
			for(Quote quote:quoteMap.subMap(new Date(start), new Date(end+1)).values()){
				if(highestPrice<quote.getHigh()) highestPrice = quote.getHigh();
				if(lowestPrice>quote.getLow()) lowestPrice = quote.getLow();
			}
			if(highestPrice<lowestPrice){
				highestPrice = 1;
				lowestPrice = 0;
			}
			priceAxis.setRange(lowestPrice*0.995,highestPrice*1.005);
		}
	}
	public void adjustDomainRange(){
		Range range = dateAxis.getRange();
		long end = (long)range.getUpperBound();
		long start = (long)range.getLowerBound();
		if(currentContract!=null){
			TreeMap<Date,Quote> quoteMap = ResourceManager.getHistoricalData(currentContract, currentBarsize, adjusted);
			if(!quoteMap.isEmpty()){
				long timeLowerBound = quoteMap.firstKey().getTime() - BarSize.Day1.getLengthInMills()*5;
				long timeUpperBound = quoteMap.lastKey().getTime() + BarSize.Day1.getLengthInMills()*5;
				setDateRange(new Date(start<timeLowerBound?timeLowerBound:start),new Date(end>timeUpperBound?timeUpperBound:end));
			}
		}
	}
	public void addQuote(Quote quote){
		existDataset.add(new OHLCDataItem(quote.getTime(),quote.getOpen(),quote.getHigh(),quote.getLow(),quote.getClose(),(double)quote.getVolume()));
		DefaultOHLCDataset result = new DefaultOHLCDataset(MyUtil.ContractIdentifier(currentContract),existDataset.toArray(new OHLCDataItem[existDataset.size()]));
		setDataset(result);
	}
	private DefaultOHLCDataset getDataSet(Contract contract,BarSize barSize, boolean adjusted){
		TreeMap<Date,Quote> quoteMap = ResourceManager.getHistoricalData(contract, barSize, adjusted);
		if(!existDataset.isEmpty()){
			existDataset.clear();
		}
		for(Quote quote:quoteMap.values()){
			existDataset.add(new OHLCDataItem(quote.getTime(),quote.getOpen(),quote.getHigh(),quote.getLow(),quote.getClose(),(double)quote.getVolume()));
		}
		DefaultOHLCDataset result = new DefaultOHLCDataset(MyUtil.ContractIdentifier(contract),existDataset.toArray(new OHLCDataItem[existDataset.size()]));
		return result;
	}
	private DefaultOHLCDataset getEmptyDataSet(){
		DefaultOHLCDataset result = new DefaultOHLCDataset("empty",existDataset.toArray(new OHLCDataItem[existDataset.size()]));
		return result;
	}
	private void setDataset(DefaultOHLCDataset dataset){
		candleChart.setDataset(dataset);
	}
	public void setAdjusted(boolean flag){
		adjusted = flag;
	}
	public boolean isAdjusted(){
		return adjusted;
	}
	public Quote getCurrentQuote(){
		if(currentMarkerPosition!=null){
			return ResourceManager.getHistoricalData(currentContract, currentBarsize, adjusted).get(currentMarkerPosition);
		}
		return null;
	}
	public void feedReport(BacktestReport report){
		for(TradingSignal signal:report.getAccount().getTradingHistory()){
			tradingHistoryMap.put(signal.getTime(), signal);
		}
	}
	protected void updateInformationWindow(){
		Quote currentQuote= getCurrentQuote();
		if(currentQuote!=null){
			ResourceManager.setInfoWindowModel(currentQuote.getTime(),currentQuote.getOpen(), currentQuote.getClose(), currentQuote.getHigh(), currentQuote.getLow(), (long)currentQuote.getVolume());
		}else{
			LOGGER.warning("never ever try to get the quote when no price bar is selected");
		}
	}
	protected void updateBacktestingInfoWindow(){
		Quote currentQuote = getCurrentQuote();
		if(currentQuote!=null && tradingHistoryMap.containsKey(currentQuote.getTime())){
			TradingSignal signal = tradingHistoryMap.get(currentQuote.getTime());
			ResourceManager.getBacktestingWindow().updateSignalInfo(signal);
		}else{
			ResourceManager.getBacktestingWindow().cleanSignalInfo();
		}
	}
	abstract void onDashMarkerSet();
}
