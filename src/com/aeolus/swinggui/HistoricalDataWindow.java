package com.aeolus.swinggui;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;

import com.aeolus.resources.manager.ResourceManager;

import java.awt.Rectangle;

public class HistoricalDataWindow extends JPanel {
	private ControlPanel controlPanel;
	private InforWindow infoWindow;
	/**
	 * Create the panel.
	 */
	public HistoricalDataWindow() {
		ResourceManager.registerMainWindow(this);
		setLayout(null);
		
		controlPanel = new ControlPanel();
		controlPanel.setBounds(0, 0, 243, 423);
		add(controlPanel);
		
		ChartHost chartHost = new ChartHost();
		chartHost.setBounds(244, 0, 1011, 639);
		add(chartHost);
		
		infoWindow = new InforWindow();
		infoWindow.setBounds(0, 423, 243, 216);
		add(infoWindow);

	}
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	public InforWindow getInfoWindow() {
		return infoWindow;
	}
}
