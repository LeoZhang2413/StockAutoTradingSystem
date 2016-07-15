package com.aeolus.swinggui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.aeolus.account.CashAccount;
import com.aeolus.backtesting.BacktestReport;
import com.aeolus.backtesting.BacktestingTool;
import com.aeolus.constant.BarSize;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.strategy.SignalType;
import com.aeolus.strategy.TradingSignal;
import com.aeolus.strategy.pool.DailyMeanReverting;
import com.aeolus.util.ContractFactory;
import com.ib.client.Contract;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JTextArea;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

public class BacktestingWindow extends JPanel {
	
	private JTextField txtSecurityCode;
	private BacktestModel backtestModel = new BacktestModel();
	private JTextArea backtestInfo;
	public BacktestingWindow(BacktestModel backtestModel){
		this();
	}
	/**
	 * Create the panel.
	 */
	public BacktestingWindow() {
		ResourceManager.registerBacktestingWindow(this);
		//backtestModel.setSignal(new TradingSignal(SignalType.HOLD, ContractFactory.stockContract("AAPL"), new Date(), 1000, 40.0));
		setLayout(null);
		
		BackTestingStockChartHost backTestingStockChartHost = new BackTestingStockChartHost();
		backTestingStockChartHost.setBounds(246, 31, 736, 439);
		add(backTestingStockChartHost);
		
		txtSecurityCode = new JTextField();
		txtSecurityCode.setText("APPL");
		txtSecurityCode.setBounds(6, 31, 109, 26);
		add(txtSecurityCode);
		txtSecurityCode.setColumns(10);
		
		backtestInfo = new JTextArea();
		backtestInfo.setBounds(6, 225, 225, 245);
		add(backtestInfo);
		
		JButton btnBackTest = new JButton("back test");
		btnBackTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Contract contract = ContractFactory.stockContract(txtSecurityCode.getText());
				BacktestingTool tool = new BacktestingTool();
				BacktestReport report = tool.testingStrategy(new DailyMeanReverting(new CashAccount(1000000),contract, BarSize.Day1));
				ResourceManager.getBackTestingChart().getCandleChart().setAdjusted(true);
				ResourceManager.getBackTestingChart().getCandleChart().changeContent(contract, BarSize.Day1);
				ResourceManager.getBackTestingChart().setTitle(ContractFactory.stockContract(txtSecurityCode.getText()));
				ResourceManager.getBackTestingChart().setBacktestReportChart(report);
				ResourceManager.getBackTestingChart().getCandleChart().feedReport(report);
			}
		});
		btnBackTest.setBounds(117, 31, 117, 29);
		add(btnBackTest);
		initDataBindings();
	}
	public void updateSignalInfo(TradingSignal signal){
		backtestModel.setSignalInfo(signal.getSignalInfo());
	}
	public void cleanSignalInfo(){
		backtestModel.setSignalInfo("no trading record");
	}
	protected void initDataBindings() {
		BeanProperty<BacktestModel, String> backtestModelBeanProperty = BeanProperty.create("signalInfo");
		BeanProperty<JTextArea, String> jTextAreaBeanProperty = BeanProperty.create("text");
		AutoBinding<BacktestModel, String, JTextArea, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, backtestModel, backtestModelBeanProperty, backtestInfo, jTextAreaBeanProperty);
		autoBinding.bind();
	}
}
