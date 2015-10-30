package com.aeolus.swinggui;

import javax.swing.JPanel;

import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.manager.ResourceManager;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class ChartHost extends JPanel {

	/**
	 * Create the panel.
	 */
	public ChartHost() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = ResourceManager.getStockChart().getPanel();
		add(panel, BorderLayout.CENTER);
	}

}
