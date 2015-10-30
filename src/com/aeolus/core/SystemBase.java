package com.aeolus.core;
import java.util.logging.Logger;

import com.aeolus.constant.BarSize;
import com.aeolus.constant.SystemParams;
import com.aeolus.resources.manager.ResourceManager;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;

public class SystemBase {
	private static Logger LOGGER = Logger.getLogger(SystemBase.class.getName());
	private SystemCore core = new SystemCore();
	private EClientSocket m_client = new EClientSocket(core);
	public void connect(){
		if(m_client.isConnected()){
			LOGGER.warning("reject connection request because it is already connected");
		}else{
			m_client.eConnect(SystemParams.IP, SystemParams.PORT, SystemParams.clientId);
			if (m_client.isConnected()) {
				ResourceManager.setConnected(true);
				LOGGER.info("successfully connected");
			}else{
				LOGGER.info("connection failed, please try again");
			}
		}
	}
	public void disconnect(){
		m_client.eDisconnect();
		if(!m_client.isConnected()){
			ResourceManager.setConnected(false);
		}
	}
	public void RequestHistoricalData(Contract contract, String startTime, String endTime, BarSize barSize){
		ReqHistoricalDataTask reqHistoricalDataTask = new ReqHistoricalDataTask(this, contract, startTime, endTime, barSize);
		core.getTaskPool().addTask(reqHistoricalDataTask);
		reqHistoricalDataTask.startTask();
	}
	/*public void RequestHistoricalData(Contract contract, String endTime, String duration, BarSize barSize){
		ReqHistoricalDataTask reqHistoricalDataTask = new ReqHistoricalDataTask(this,core.nextAvailableId(), contract, endTime, duration, barSize);
		core.getTaskPool().addTask(reqHistoricalDataTask);
		reqHistoricalDataTask.startTask(m_client);
	}*/
	//update the recent 5 years data
	/*public void updateMostRecentHistoricalData(Contract contract, BarSize barSize){
		TreeMap<Long,Quote> quoteMap = HistoricalDataManager.getHistoricalData(contract).getQuotes(barSize);
		String lastDate = quoteMap.size()==0?"19710101":quoteMap.lastEntry().getValue().getTimeStamp();
		String duration = MyUtil.getDurationFromNow(lastDate);
		RequestHistoricalData(contract,MyUtil.currentTime(),duration,barSize);
	}*/
	public SystemCore getCore() {
		return core;
	}
	public EClientSocket getM_client() {
		return m_client;
	}
}
