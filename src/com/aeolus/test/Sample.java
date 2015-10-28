package com.aeolus.test;
/**
* Simple program showing : -
* 1) candlestick chart with TimeSeries()
*      and dynamically add a time series
* 2) Dynamic update
* 3) change the renderer of the series
* 4) dynamically add a new plot
*
* @author Maddy
*
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.HighLowRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

public class Sample extends JFrame implements ActionListener
{
   private static final long serialVersionUID = 834643894170724191L;

   JButton btn1 = new JButton("change renderer");
   JButton btn2 = new JButton("update");
   JButton btn3 = new JButton("add series");
   JButton btn4 = new JButton("add Plot");
   TimeSeries s1;
   DateAxis domainAxis;
   private AbstractXYItemRenderer Renderer;
   CombinedDomainXYPlot combinedplot;
   XYPlot mainPlot;
   int seriesnumber=2;//already there are 2 series added to the chart
   int x=5;
   public Sample(String stockSymbol)
   {
      super("MyDemo");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      domainAxis = new DateAxis("Date");
      NumberAxis rangeAxis = new NumberAxis("Price");
      Renderer = new CandlestickRenderer();
      XYDataset dataset = getDataSet(stockSymbol);

      mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, Renderer);
      // Create a line series
      TimeSeriesCollection timecollection = createMADataset(2);//value just to make difference in the new dataset
      mainPlot.setDataset(1, timecollection);
      mainPlot.setRenderer(1, new XYLineAndShapeRenderer(true, false));
      mainPlot.getRenderer(1).setSeriesPaint(0, Color.blue);
      
      combinedplot = new CombinedDomainXYPlot(domainAxis);
      combinedplot.setDomainGridlinePaint(Color.white);
      combinedplot.setDomainGridlinesVisible(true);
      combinedplot.add(mainPlot, 3);

      // Do some setting up, see the API Doc
      Renderer.setSeriesPaint(0, Color.BLACK);
      rangeAxis.setAutoRangeIncludesZero(false);

      // Now create the chart and chart panel
      JFreeChart chart = new JFreeChart(stockSymbol, null, combinedplot, false);
      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setPreferredSize(new Dimension(600, 300));

      this.setLayout(new BorderLayout());
      this.add(chartPanel,BorderLayout.CENTER);
      this.add(btn1,BorderLayout.NORTH);
      this.add(btn2,BorderLayout.SOUTH);
      this.add(btn3,BorderLayout.WEST);
      this.add(btn4,BorderLayout.EAST);
      btn1.addActionListener(this);
      btn2.addActionListener(this);
      btn3.addActionListener(this);
      btn4.addActionListener(this);
      this.pack();
   }

   protected AbstractXYDataset getDataSet(String stockSymbol)
   {
      // This is the dataset we are going to create
      DefaultOHLCDataset result = null;
      // This is the data needed for the dataset
      OHLCDataItem[] data;

      // This is where we go get the data, replace with your own data source
      data = getData(stockSymbol);

      // Create a dataset, an Open, High, Low, Close dataset
      result = new DefaultOHLCDataset(stockSymbol, data);

      return result;
   }

   // This method uses yahoo finance to get the OHLC data
   protected OHLCDataItem[] getData(String stockSymbol)
   {
      List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
      try
      {
         String strUrl = "http://ichart.finance.yahoo.com/table.csv?s=" + stockSymbol
               + "&a=0&b=1&c=2008&d=3&e=30&f=2008&ignore=.csv";
         URL url = new URL(strUrl);
         BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
         DateFormat df = new SimpleDateFormat("y-M-d");

         String inputLine;
         in.readLine();
         while ((inputLine = in.readLine()) != null)
         {
            StringTokenizer st = new StringTokenizer(inputLine, ",");

            Date date = df.parse(st.nextToken());
            double open = Double.parseDouble(st.nextToken());
            double high = Double.parseDouble(st.nextToken());
            double low = Double.parseDouble(st.nextToken());
            double close = Double.parseDouble(st.nextToken());
            double volume = Double.parseDouble(st.nextToken());
            // double adjClose = Double.parseDouble(st.nextToken());

            OHLCDataItem item = new OHLCDataItem(date, open, high, low, close, volume);
            dataItems.add(item);
         }
         in.close();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
      // Data from Yahoo is from newest to oldest. Reverse so it is oldest to newest
      Collections.reverse(dataItems);

      // Convert the list into an array
      OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);

      return data;
   }


   public XYPlot createPlot(XYDataset dataset)
   {

      NumberAxis RangeaxisValue = new NumberAxis();

      XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
      renderer.setSeriesPaint(0, Color.green);
      renderer.setSeriesPaint(1, Color.blue);

      XYPlot newPlot = new XYPlot(dataset, domainAxis, RangeaxisValue, renderer);
      newPlot.setBackgroundPaint(Color.white);
      newPlot.setDomainGridlinePaint(Color.gray);
      newPlot.setRangeGridlinePaint(Color.gray);
      newPlot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
      return newPlot;
   }

   private TimeSeriesCollection createMADataset(int s)
   {
      s1 = new TimeSeries("Budget", "Year", "$ Million");
      try
      {
         s1.add(new Day(1, 1, 2008), 37.8+s);
         s1.add(new Day(8, 1, 2008), 35.3+s);
         s1.add(new Day(15, 1, 2008), 34.8+s);
         s1.add(new Day(22, 1, 2008), 33.6+s);
         s1.add(new Day(1, 2, 2008), 32.8+s);
         s1.add(new Day(8, 2, 2008), 31.3+s);
         s1.add(new Day(15, 2, 2008), 29.9+s);
         s1.add(new Day(22, 2, 2008), 27.7+s);
         s1.add(new Day(1, 3, 2008), 26.2+s);
         s1.add(new Day(8, 3, 2008), 26.8+s);
         s1.add(new Day(15, 3, 2008), 27.6+s);
         s1.add(new Day(22, 3, 2008), 28.9+s);
         s1.add(new Day(1, 4, 2008), 27.7+s);
         s1.add(new Day(8, 4, 2008), 29.3+s);
         s1.add(new Day(15, 4, 2008), 27.9+s);
         s1.add(new Day(22, 4, 2008), 31.8+s);
         s1.add(new Day(1, 5, 2008), 30.0+s);
      } catch (Exception exception)
      {
         System.err.println(exception.getMessage());
      }
      TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(s1);
      return timeseriescollection;
   }

   public static void main(String[] args)
   {
      new Sample("MSFT").setVisible(true);
   }
   static int cnt=0;
   @Override
   public void actionPerformed(ActionEvent e)
   {
      if(e.getSource()==btn1)
      {
         System.out.println("Renderer");
         if(cnt==0){
            Renderer = new XYLineAndShapeRenderer(true, false);
            try{
            mainPlot.setRenderer(1, new XYLineAndShapeRenderer(true, false));
            }catch (Exception ex) {}
            cnt++;
         }
         else if (cnt ==1)
         {
            Renderer = new HighLowRenderer();
            cnt++;
         }else if (cnt ==2)
         {
            Renderer = new CandlestickRenderer();
            cnt++;
         }
         else if (cnt ==3)
         {
            mainPlot.setRenderer(1, new XYBarRenderer());
            cnt=0;
         }
         mainPlot.setRenderer(0, Renderer);
      }
      else if(e.getSource()==btn2)
      {
         System.out.println("Update");
         s1.addOrUpdate(new Day(1, 5, 2008), getrandomNumber());
      }
      else if(e.getSource()==btn3)
      {   
         System.out.println("new Series");
         // Create a line series
         TimeSeriesCollection timecollection = createMADataset(x);
         mainPlot.setDataset(seriesnumber, timecollection);
         mainPlot.setRenderer(seriesnumber, new XYLineAndShapeRenderer(true, false));
         mainPlot.getRenderer(seriesnumber++).setSeriesPaint(0, getRandomColor());
         x = x+4;
      }
      else if(e.getSource()==btn4)
      {
         System.out.println("new Plot");
         combinedplot.add(createPlot(createMADataset(-x)), 1);
      }
   }
   public Color getRandomColor()
   {
      Random numGen = new Random();
      return new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256));
   }
   public double getrandomNumber()
   {
      Random numGen = new Random();
      return numGen.nextDouble()*100;
   }
}