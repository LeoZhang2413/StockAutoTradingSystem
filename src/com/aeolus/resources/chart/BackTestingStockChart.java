package com.aeolus.resources.chart;


import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;

import com.aeolus.backtesting.BacktestReport;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public class BackTestingStockChart{
	CandleChart candleChart;
	TimeSeriesChart timeSeriesChart;
	JFreeChart jFreeChart;
	protected ChartPanel chartPanel;
	public BackTestingStockChart(){
		candleChart = new CandleChart(){

			@Override
			void onDashMarkerSet() {
				// TODO Auto-generated method stub
				updateBacktestingInfoWindow();
			}};
		timeSeriesChart = new TimeSeriesChart("backtest result");
		timeSeriesChart.getXYPlot().setDomainAxis(candleChart.dateAxis);
		final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(candleChart.dateAxis);
        plot.setGap(10.0);
        // add the subplots...
        plot.add(candleChart.getXYPlot(), 1);
        plot.add(timeSeriesChart.getXYPlot(), 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        // return a new chart containing the overlaid plot...
        //new JFreeChart("empty",null,mainPlot,false);
        jFreeChart = new JFreeChart("empty",
                              null, plot, true);
        chartPanel = new ChartPanel(jFreeChart);
		chartPanel.setMouseZoomable(true, false);
		chartPanel.setPreferredSize(new Dimension(1000,500));
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				onMouseWheelUpdate();
			}});
		chartPanel.setFocusable(true);
		chartPanel.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chartPanel.grabFocus();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		chartPanel.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent key) {
				switch(key.getKeyCode()){
					case 37:{
						candleChart.MoveDashMarker(false);
						break;
					}
					case 39:{
						candleChart.MoveDashMarker(true);
						break;
					}
					default:{
						break;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public void onMouseWheelUpdate(){
		candleChart.adjustDomainRange();
		candleChart.adjustValueRange();
	}
	public void setTitle(Contract contract){
		chartPanel.getChart().setTitle(MyUtil.ContractIdentifier(contract));
	}
	public ChartPanel getChartPanel() {
		return chartPanel;
	}
	public CandleChart getCandleChart() {
		return candleChart;
	}
	public TimeSeriesChart getTimeSeriesChart() {
		return timeSeriesChart;
	}
	public void setBacktestReportChart(BacktestReport report){
		timeSeriesChart.addNewSeries("networth", report.getNetworth());
	}
}
