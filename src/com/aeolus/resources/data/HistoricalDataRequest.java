package com.aeolus.resources.data;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.aeolus.constant.SystemParams;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.TagValue;

public class HistoricalDataRequest {
	private EClientSocket client;
	private static Lock historicalRequestLock = new ReentrantLock();
	public HistoricalDataRequest(EClientSocket client){
		this.client = client;
	}
	public void sendRequest(final int reqId, final Contract contract, final String endTime, final String duration, final String barSizeSetting, final String whatToShow, final int useRTH, final int formatDate, final List<TagValue> chartOptions){
		new Thread(){
			public void run(){
				HistoricalDataRequest.historicalRequestLock.lock();
				client.reqHistoricalData(reqId, contract, endTime, duration, barSizeSetting, whatToShow, useRTH, formatDate,chartOptions);
				try {
					Thread.sleep(SystemParams.historicalDataFeedInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				HistoricalDataRequest.historicalRequestLock.unlock();
			}
		}.start();
	}
}
