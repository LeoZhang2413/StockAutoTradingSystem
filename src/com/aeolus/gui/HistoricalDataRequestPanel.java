package com.aeolus.gui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import com.aeolus.constant.*;
import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.data.Quote;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.util.ContractFactory;
import com.aeolus.util.MyUtil;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Table;

import java.util.Date;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.widgets.DateTime;

public class HistoricalDataRequestPanel extends Composite {
	private Text symbol_text;
	private Text type_text;
	private Text barSize_text;
	private BarSize barSize;
	private DateTime fromDate;
	private DateTime toDate;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public HistoricalDataRequestPanel(Composite parent, int style) {
		super(parent, style);
		
		Label symbolLabel = new Label(this, SWT.NONE);
		symbolLabel.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.BOLD));
		symbolLabel.setBounds(10, 21, 88, 23);
		symbolLabel.setText("Symbol");
		
		symbol_text = new Text(this, SWT.BORDER);
		symbol_text.setBounds(138, 15, 88, 34);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(barSize != null){
					ResourceManager.getStockChart().changeContent(ContractFactory.stockContract(symbol_text.getText()), barSize);
				}
			}
		});
		btnNewButton.setBounds(10, 258, 216, 34);
		btnNewButton.setText("load data");
		
		Label typeLabel = new Label(this, SWT.NONE);
		typeLabel.setText("Type");
		typeLabel.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.BOLD));
		typeLabel.setBounds(10, 54, 88, 23);
		
		type_text = new Text(this, SWT.BORDER);
		type_text.setText("STK");
		type_text.setBounds(138, 50, 88, 34);
		
		Label barSizeLabel = new Label(this, SWT.NONE);
		barSizeLabel.setText("BarSize");
		barSizeLabel.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.BOLD));
		barSizeLabel.setBounds(10, 89, 88, 23);
		
		barSize_text = new Text(this, SWT.BORDER);
		barSize_text.setEditable(false);
		barSize_text.setText("");
		barSize_text.setBounds(138, 83, 88, 34);
		
		Button sec1 = new Button(this, SWT.NONE);
		sec1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Second1;
				barSize_text.setText(barSize.getValue());
			}
		});
		sec1.setBounds(10, 118, 68, 29);
		sec1.setText("1 sec");
		
		Button sec5 = new Button(this, SWT.NONE);
		sec5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Second5;
				barSize_text.setText(barSize.getValue());
			}
		});
		sec5.setText("5 secs");
		sec5.setBounds(84, 118, 68, 29);
		
		Button sec10 = new Button(this, SWT.NONE);
		sec10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Second10;
				barSize_text.setText(barSize.getValue());
			}
		});
		sec10.setText("10 sec");
		sec10.setBounds(158, 118, 68, 29);
		
		Button sec15 = new Button(this, SWT.NONE);
		sec15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Second15;
				barSize_text.setText(barSize.getValue());
			}
		});
		sec15.setText("15 sec");
		sec15.setBounds(10, 153, 68, 29);
		
		Button sec30 = new Button(this, SWT.NONE);
		sec30.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Second30;
				barSize_text.setText(barSize.getValue());
			}
		});
		sec30.setText("30 sec");
		sec30.setBounds(84, 153, 68, 29);
		
		Button min1 = new Button(this, SWT.NONE);
		min1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Minute1;
				barSize_text.setText(barSize.getValue());
			}
		});
		min1.setText("1 min");
		min1.setBounds(158, 153, 68, 29);
		
		Button min5 = new Button(this, SWT.NONE);
		min5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Minute5;
				barSize_text.setText(barSize.getValue());
			}
		});
		min5.setText("5 mins");
		min5.setBounds(10, 188, 68, 29);
		
		Button min30 = new Button(this, SWT.NONE);
		min30.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Minute30;
				barSize_text.setText(barSize.getValue());
			}
		});
		min30.setText("30 mins");
		min30.setBounds(84, 188, 68, 29);
		
		Button hour1 = new Button(this, SWT.NONE);
		hour1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Hour1;
				barSize_text.setText(barSize.getValue());
			}
		});
		hour1.setText("1 hour");
		hour1.setBounds(158, 188, 68, 29);
		
		Button day1 = new Button(this, SWT.NONE);
		day1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Day1;
				barSize_text.setText(barSize.getValue());
			}
		});
		day1.setText("1 day");
		day1.setBounds(10, 223, 68, 29);
		
		Button week1 = new Button(this, SWT.NONE);
		week1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Week1;
				barSize_text.setText(barSize.getValue());
			}
		});
		week1.setText("1 week");
		week1.setBounds(84, 223, 68, 29);
		
		Button month1 = new Button(this, SWT.NONE);
		month1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				barSize = BarSize.Month1;
				barSize_text.setText(barSize.getValue());
			}
		});
		month1.setText("1 month");
		month1.setBounds(158, 223, 68, 29);
		
		fromDate = new DateTime(this, SWT.BORDER);
		fromDate.setBounds(70, 324, 156, 39);
		
		toDate = new DateTime(this, SWT.BORDER);
		toDate.setBounds(70, 361, 156, 39);
		
		Label fromLabel = new Label(this, SWT.NONE);
		fromLabel.setText("from");
		fromLabel.setFont(SWTResourceManager.getFont("Ubuntu", 12, SWT.BOLD));
		fromLabel.setBounds(10, 332, 88, 23);
		
		Label toLabel = new Label(this, SWT.NONE);
		toLabel.setText("to");
		toLabel.setFont(SWTResourceManager.getFont("Ubuntu", 12, SWT.BOLD));
		toLabel.setBounds(10, 369, 88, 23);
		
		Button btnDownloadData = new Button(this, SWT.NONE);
		btnDownloadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ResourceManager.downloadHistoricalData(symbol_text.getText(), MyUtil.dateTimeToString(fromDate), MyUtil.dateTimeToString(toDate), barSize);
			}
		});
		btnDownloadData.setText("Download");
		btnDownloadData.setBounds(124, 406, 102, 93);
		
		Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 298, 218, 20);
		
		Button connectButton = new Button(this, SWT.NONE);
		connectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ResourceManager.connectToServer();
			}
		});
		connectButton.setText("connect");
		connectButton.setBounds(10, 516, 216, 34);
		
		Button btnAdjusteddata = new Button(this, SWT.NONE);
		btnAdjusteddata.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ResourceManager.getAdjustedRatio(ContractFactory.stockContract(symbol_text.getText()));
			}
		});
		btnAdjusteddata.setText("Yahoo");
		btnAdjusteddata.setBounds(10, 406, 96, 93);
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/*for(Entry<Date,Quote> entry:ResourceManager.getOriginalHistoricalData(ContractFactory.stockContract(symbol_text.getText())).getQuotes(barSize).entrySet()){
					System.out.println(entry.getKey()+"    "+entry.getValue());
				}*/
			}
		});
		btnNewButton_1.setBounds(10, 556, 93, 29);
		btnNewButton_1.setText("TestResult");
		
		Button btnNewButton_2 = new Button(this, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ResourceManager.getStockChart().setAdjusted(!ResourceManager.getStockChart().isAdjusted());
				if(barSize != null){
					ResourceManager.getStockChart().changeContent(ContractFactory.stockContract(symbol_text.getText()), barSize);
				}
			}
		});
		btnNewButton_2.setBounds(133, 556, 93, 29);
		btnNewButton_2.setText("toggle DR");
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
