package com.aeolus.resources.chart;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.aeolus.resources.data.Quote;
import com.aeolus.resources.manager.ResourceManager;

public class TimeSeriesChart {
	XYPlot timeSeriesXYPlot;
	XYSeriesCollection dataset;
	XYItemRenderer renderer;
	protected DateAxis dateAxis;
	protected NumberAxis priceAxis;
	public TimeSeriesChart(String YAxisLabel){
		dataset = createDataset2();
		renderer = new StandardXYItemRenderer();
		dateAxis = new DateAxis("Date");
		priceAxis = new NumberAxis(YAxisLabel);
		priceAxis.setAutoRangeIncludesZero(false);
        timeSeriesXYPlot = new XYPlot(dataset, dateAxis, priceAxis, renderer);
        timeSeriesXYPlot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
	}
	public XYPlot getXYPlot(){
		return timeSeriesXYPlot;
	}
	public void addNewSeries(String name, Map<Date,Double> data){
		XYSeries series = new XYSeries(name);
		for(Entry<Date,Double> entry:data.entrySet()){
			series.add(entry.getKey().getTime(), entry.getValue());
		}
		dataset.addSeries(series);
	}
	public void removeAllSeries(){
		dataset.removeAllSeries();
	}
	private XYSeriesCollection createDataset2() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        return dataset;

    }
}