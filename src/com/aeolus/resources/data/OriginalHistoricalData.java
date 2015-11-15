package com.aeolus.resources.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.aeolus.constant.SystemParams;
import com.ib.client.Contract;

public class OriginalHistoricalData extends HistoricalData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(OriginalHistoricalData.class.getName());
	public OriginalHistoricalData(Contract contract) {
		super(contract);
	}
	private TreeMap<Date,Double> adjustedClose = new TreeMap<Date,Double>();// adjusted close price / market close price
	public synchronized void addAdjustedCloseData(TreeMap<Date,Double> adjustedData){
		adjustedClose.putAll(adjustedData);
	}
	public synchronized TreeMap<Date,Double> getAdjustedClose(){
		return adjustedClose;
	}
	public static OriginalHistoricalData loadFromDisk(String fileName){
		FileInputStream in;
		try {
			in = new FileInputStream(SystemParams.dataRootDir+fileName);
			ObjectInputStream stream = new ObjectInputStream(in);
			return (OriginalHistoricalData) stream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
	public void writeToDisk(){
		FileOutputStream out;
		try {
			LOGGER.info("writing "+this.identifier);
			out = new FileOutputStream(SystemParams.dataRootDir+identifier);
			ObjectOutputStream stream = new ObjectOutputStream(out);
			stream.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
