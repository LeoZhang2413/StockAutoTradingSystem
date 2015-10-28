package com.aeolus.test;

import java.awt.Dimension;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import com.aeolus.constant.BarSize;
import com.aeolus.core.SystemBase;
import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.data.OriginalHistoricalDataManager;
import com.aeolus.resources.data.Quote;
import com.aeolus.util.ContractFactory;
import com.aeolus.util.MyUtil;
public class MyGUI {
  public static void main(String[] a) {
	/*OriginalHistoricalDataManager.loadFromDisk();
	if(OriginalHistoricalDataManager.isEmpty()){
		SystemBase base = new SystemBase();
		base.connect();
		base.RequestHistoricalData(ContractFactory.stockContract("SBUX"), "20151010 00:00:00 GMT", MyUtil.currentTime(), BarSize.Day1);
	}else{
	}*/
	//new MainWindow().setVisible(true);
	/*StockChart c = new StockChart();
	c.dataset.
    JPanel panel = new JPanel();
    JButton okButton = new JButton("OK");
    panel.add(okButton);
    JButton cancelButton = new JButton("Cancel");
    panel.add(cancelButton);
    panel.add(c.getChartPanel());
    JFrame frame = new JFrame("Oval Sample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(panel);
    frame.setSize(300, 200);
    frame.setVisible(true);*/
  }
}
