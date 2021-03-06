package com.aeolus.resources.chart;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;

import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.Quote;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.util.ChartHelper;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public class StockChart {
	private static Logger LOGGER = Logger.getLogger(StockChart.class.getName());
	private ChartPanel chartPanel;
	private CandleChart candleChart;
	public ChartPanel getChartPanel(){
		return chartPanel;
	}
	public CandleChart getCandleChart(){
		return candleChart;
	}
	public void setTitle(Contract contract){
		chartPanel.getChart().setTitle(MyUtil.ContractIdentifier(contract));
	}
	public StockChart(){
		candleChart = new CandleChart(){
			@Override
			void onDashMarkerSet() {
				// TODO Auto-generated method stub
				this.updateInformationWindow();
			}};
		JFreeChart chart = new JFreeChart("empty",null,candleChart.getXYPlot(),false);
		chartPanel = new ChartPanel(chart);
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
}