package com.aeolus.gui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.manager.ResourceManager;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StockChartPanel extends Composite {
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StockChartPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		Frame fileTableFrame = SWT_AWT.new_Frame(this);
		fileTableFrame.add(ResourceManager.getStockChart().getPanel());
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
