package com.aeolus.swinggui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.aeolus.resources.manager.ResourceManager;

public class BackTestingStockChartHost extends JPanel {

	/**
	 * Create the panel.
	 */
	public BackTestingStockChartHost() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = ResourceManager.getBackTestingChart().getChartPanel();
		add(panel, BorderLayout.CENTER);
	}

}
