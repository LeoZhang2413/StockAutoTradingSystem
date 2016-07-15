package com.aeolus.swinggui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.AdjustedHistoricalDataManager;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.util.ContractFactory;
import com.aeolus.util.MyUtil;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JProgressBar;
import javax.swing.JTable;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControlPanel extends JPanel {
	private static Logger LOGGER = Logger.getLogger(ControlPanel.class.getName());
	private ProcessStatusModel processStatus = new ProcessStatusModel();
	private JTextField symbol_text;
	private JComboBox<String> typeBox;
	private JLabel lblBarSize;
	private JComboBox<BarSize> barSizeBox;
	private JButton btnDownloadHistoricalData;
	private JButton btnLoad;
	private JDatePanelImpl from;
	private JDatePanelImpl to;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JButton btnDownload;
	private JButton btnDr;
	private UtilDateModel fromModel;
	private UtilDateModel toModel;
	private JProgressBar progressBar;
	/**
	 * Create the panel.
	 */
	public ControlPanel() {
		setLayout(null);
		
		JLabel label = new JLabel("Symbol");
		label.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 17));
		label.setBounds(12, 12, 86, 34);
		add(label);
		
		symbol_text = new JTextField();
		symbol_text.setText("AAPL");
		symbol_text.setColumns(10);
		symbol_text.setBounds(99, 18, 114, 27);
		add(symbol_text);
		
		JLabel label_1 = new JLabel("Type");
		label_1.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 17));
		label_1.setBounds(12, 44, 86, 34);
		add(label_1);
		
		typeBox = new JComboBox();
		typeBox.setBounds(99, 51, 114, 24);
		add(typeBox);
		
		lblBarSize = new JLabel("Bar Size");
		lblBarSize.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 17));
		lblBarSize.setBounds(12, 77, 96, 34);
		add(lblBarSize);
		
		barSizeBox = new JComboBox();
		barSizeBox.setBounds(99, 84, 114, 24);
		add(barSizeBox);
		
		JButton btnNewButton = new JButton("connect");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResourceManager.connectToServer();
			}
		});
		btnNewButton.setBounds(12, 261, 214, 34);
		add(btnNewButton);
		
		btnDownloadHistoricalData = new JButton("test");
		btnDownloadHistoricalData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//ResourceManager.getHistoricalData(ContractFactory.stockContract(symbol_text.getText()), (BarSize)barSizeBox.getModel().getSelectedItem(), false)
			}
		});
		btnDownloadHistoricalData.setBounds(124, 375, 102, 34);
		add(btnDownloadHistoricalData);
		
		btnLoad = new JButton("Origin");
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResourceManager.getStockChart().getCandleChart().setAdjusted(false);
				ResourceManager.getStockChart().getCandleChart().changeContent(ContractFactory.stockContract(symbol_text.getText()), (BarSize)barSizeBox.getModel().getSelectedItem());
				ResourceManager.getStockChart().setTitle(ContractFactory.stockContract(symbol_text.getText()));
			}
		});
		btnLoad.setBounds(12, 302, 96, 61);
		add(btnLoad);
		
		fillTypeAndBarSize();
		fromModel = new UtilDateModel();
		fromModel.setDate(2015, 6, 1);
		fromModel.setSelected(true);
		JDatePanelImpl from = new JDatePanelImpl(fromModel);
		JDatePickerImpl fromDatePicker = new JDatePickerImpl(from);
		fromDatePicker.setBounds(67, 118, 146, 27);
		add(fromDatePicker);
		
		toModel = new UtilDateModel();
		toModel.setDate(2015, 10, 28);
		toModel.setSelected(true);
		JDatePanelImpl to = new JDatePanelImpl(toModel);
		JDatePickerImpl toDatePicker = new JDatePickerImpl(to);
		toDatePicker.setBounds(67, 147, 146, 27);
		add(toDatePicker);
		
		lblFrom = new JLabel("From");
		lblFrom.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 17));
		lblFrom.setBounds(12, 110, 86, 34);
		add(lblFrom);
		
		lblTo = new JLabel("To");
		lblTo.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 17));
		lblTo.setBounds(12, 140, 86, 34);
		add(lblTo);
		
		btnDownload = new JButton("download");
		btnDownload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResourceManager.downloadHistoricalDataAlongWithYahooAdjustedData(ContractFactory.stockContract(symbol_text.getText()), fromModel.getValue(), toModel.getValue(), (BarSize)barSizeBox.getModel().getSelectedItem());
			}
		});
		btnDownload.setBounds(12, 188, 214, 27);
		add(btnDownload);
		
		btnDr = new JButton("DR");
		btnDr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BarSize currentSize = (BarSize)barSizeBox.getModel().getSelectedItem();
				if(!currentSize.equals(BarSize.Week1)&&!currentSize.equals(BarSize.Month1)){
					ResourceManager.getStockChart().getCandleChart().setAdjusted(true);
					ResourceManager.getStockChart().getCandleChart().changeContent(ContractFactory.stockContract(symbol_text.getText()), (BarSize)barSizeBox.getModel().getSelectedItem());
					ResourceManager.getStockChart().setTitle(ContractFactory.stockContract(symbol_text.getText()));
				}else{
					LOGGER.warning("current bar size doesn't support DR data in this version");
				}
			}
		});
		btnDr.setBounds(124, 302, 102, 61);
		add(btnDr);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(12, 227, 214, 14);
		add(progressBar);
		
		JButton btnSave = new JButton("save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResourceManager.writeHistoricalDataToDisk();
			}
		});
		btnSave.setBounds(12, 375, 100, 34);
		add(btnSave);
		initDataBindings();
	}
	private void fillTypeAndBarSize(){
		DefaultComboBoxModel<String> typeModel = new DefaultComboBoxModel<String>();
		typeModel.addElement("STK");
		DefaultComboBoxModel<BarSize> barSizeModel = new DefaultComboBoxModel<BarSize>();
		for(BarSize size:BarSize.values()){
			if(size.equals(BarSize.Minute10)||size.equals(BarSize.Hour1)||size.equals(BarSize.Day1)){
				barSizeModel.addElement(size);
			}
		}
		typeBox.setModel(typeModel);
		barSizeBox.setModel(barSizeModel);
		
	}
	protected void initDataBindings() {
		BeanProperty<ProcessStatusModel, Integer> processStatusModelBeanProperty = BeanProperty.create("value");
		BeanProperty<JProgressBar, Integer> jProgressBarBeanProperty = BeanProperty.create("value");
		AutoBinding<ProcessStatusModel, Integer, JProgressBar, Integer> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, processStatus, processStatusModelBeanProperty, progressBar, jProgressBarBeanProperty);
		autoBinding.bind();
	}
	public void setProcessBarValue(int value){
		processStatus.setValue(value);
	}
}
