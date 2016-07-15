package com.aeolus.swinggui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.aeolus.account.CashAccount;
import com.aeolus.backtesting.BacktestingTool;
import com.aeolus.constant.BarSize;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.strategy.pool.DailyMeanReverting;
import com.aeolus.util.ContractFactory;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	BacktestingWindow backtestingWindow = new BacktestingWindow();
	HistoricalDataWindow historicalDataWindow = new HistoricalDataWindow();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1290, 776);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		historicalDataWindow.setBounds(0, 0, 1280, 720);
		contentPane.add(historicalDataWindow);
		backtestingWindow.setBounds(0, 0, 1280, 720);
		contentPane.add(backtestingWindow);
		backtestingWindow.setVisible(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu1 = new JMenu("history data");
		menuBar.add(mnNewMenu1);
		
		JMenuItem mntmHistoricalDataWindow = new JMenuItem("historical data window");
		mntmHistoricalDataWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historicalDataWindow.setVisible(true);
				backtestingWindow.setVisible(false);
			}
		});
		mnNewMenu1.add(mntmHistoricalDataWindow);
		
		JMenu mnNewMenu2 = new JMenu("backtesting");
		menuBar.add(mnNewMenu2);
		
		JMenuItem mntmBacktestingWindow = new JMenuItem("backtesting window");
		mntmBacktestingWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historicalDataWindow.setVisible(false);
				backtestingWindow.setVisible(true);
			}
		});
		mnNewMenu2.add(mntmBacktestingWindow);
		
		
	}
}
