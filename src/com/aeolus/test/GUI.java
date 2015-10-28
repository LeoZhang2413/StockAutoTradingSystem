package com.aeolus.test;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

public class GUI {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
        
        Chart chart = new Chart("Candle Stick Chart");
        ChartPanel chPanel = new ChartPanel(chart.myChart); //creating the chart panel, which extends JPanel
        chPanel.setPreferredSize(new Dimension(785, 440)); //size according to my window
        chPanel.setMouseWheelEnabled(true);
        JPanel jPanel = new JPanel();
        jPanel.add(chPanel); 
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	createAndShowGUI();
    }
}