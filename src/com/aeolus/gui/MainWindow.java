package com.aeolus.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.aeolus.resources.data.OriginalHistoricalDataManager;
import com.aeolus.resources.manager.ResourceManager;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.graphics.Point;

public class MainWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		ResourceManager.loadHistoricalDataFromDisk();
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setMinimumSize(new Point(200, 150));
		shell.setSize(1040, 764);
		shell.setText("SWT Application");
		shell.setLayout(null);
		StockChartPanel stockChart = new StockChartPanel(shell, SWT.EMBEDDED);
		stockChart.setTouchEnabled(true);
		stockChart.setBounds(256, 10, 768, 715);
		Composite historicalDataRequest = new HistoricalDataRequestPanel(shell, SWT.NONE);
		historicalDataRequest.setBounds(10, 10, 240, 648);
	}
}
