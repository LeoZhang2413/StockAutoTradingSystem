package com.aeolus.swinggui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.aeolus.resources.chart.StockChart;
import com.aeolus.resources.manager.ResourceManager;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.SystemColor;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private static ControlPanel controlPanel;
	private static InforWindow infoWindow;
	public static ControlPanel getControlPanel() {
		return controlPanel;
	}

	public static InforWindow getInfoWindow() {
		return infoWindow;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				try {
					MainWindow frame = new MainWindow();
					ResourceManager.registerMainWindow(frame);
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
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1302, 873);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.desktop);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ChartHost chartHost_ = new ChartHost();
		chartHost_.setBounds(267, 33, 1023, 811);
		contentPane.add(chartHost_);
		
		controlPanel = new ControlPanel();
		controlPanel.setBounds(12, 33, 242, 432);
		contentPane.add(controlPanel);
		
		infoWindow = new InforWindow();
		infoWindow.setBounds(12, 491, 243, 230);
		contentPane.add(infoWindow);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1302, 21);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("history data");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("backtesting");
		menuBar.add(mnNewMenu_1);
	}
}
