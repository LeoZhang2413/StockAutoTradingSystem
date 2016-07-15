package com.aeolus.constant;

public class SystemParams {
	public static final String IP = "127.0.0.1";
	public static final int PORT = 7496;
	public static final int clientId= 1;//currently only allow 1 client
	public static final long historicalDataFeedInterval = 10*1000; //in milliseconds, this parameter controls how often historical data request can be sent
	public static final String dataRootDir = "historicalData/";
}
