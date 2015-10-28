package com.aeolus.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

import org.jfree.ui.ApplicationFrame;

import com.aeolus.constant.BarSize;
import com.aeolus.core.SystemBase;
import com.aeolus.resources.data.OriginalHistoricalDataManager;
import com.aeolus.resources.data.Quote;
import com.aeolus.util.*;
public class Testing {
	public static void main(String[] args){
		ArrayList<String> a = new ArrayList<String>();
		a.iterator().next();
		/*ApplicationFrame frame = new ApplicationFrame("rome");
		frame.setVisible(true);
		OriginalHistoricalDataManager.loadFromDisk();
		if(OriginalHistoricalDataManager.isEmpty()){
			SystemBase base = new SystemBase();
			base.connect();
			base.RequestHistoricalData(ContractFactory.stockContract("SBUX"), "20141010 00:00:00 GMT", MyUtil.currentTime(), BarSize.Day1);
		}else{
			for(Entry<Date,Quote> entry :OriginalHistoricalDataManager.getOriginalHistoricalData(ContractFactory.stockContract("SBUX")).getQuotes(BarSize.Minute10).entrySet()){
				System.out.println(MyUtil.timeToString(entry.getKey().getTime())+" "+entry.getValue().getClose());
			}
		}
		*/
		//base.updateMostRecentHistoricalData(ContractFactory.stockContract("SBUX"), BarSize.Day1);
		//base.disconnect();
		/*long[] r = BarSize.Day1.validDurationInMils();
		for(int i=0;i<r.length;i++){
			System.out.println(r[i]+" : "+BarSize.getDurationExpression(r[i]));
		}*/
		//System.out.println(MyUtil.getDurationFromNow("20151024 10:15:00"));
		//System.out.println(MyUtil.currentTime());
		/*EClientSocket m_client = new EClientSocket(new MyWrapper());
		m_client.eConnect("127.0.0.1", 4001, 1);
        if (m_client.isConnected()) {
           System.out.println("connected!");
        }else{
        	System.out.println("not connected");
        }
        Vector<TagValue> m_mktDataOptions = new Vector<TagValue>();
        m_client.reqMktData( 1, ContractFactory.stockContract("SBUX"),
        		"100,101,104,105,106,107,165,221,225,233,236,258,293,294,295,318", false, m_mktDataOptions);*/
        //m_client.eDisconnect();
	}
	/*void onReqMktData() {

    	// run m_orderDlg
        m_orderDlg.init("Mkt Data Options", true, "Market Data Options", m_mktDataOptions);

        m_orderDlg.show();
        
        if( !m_orderDlg.m_rc ) {
            return;
        }
        
        m_mktDataOptions = m_orderDlg.getOptions();
        
        // req mkt data
        m_client.reqMktData( m_orderDlg.m_id, m_orderDlg.m_contract,
        		m_orderDlg.m_genericTicks, m_orderDlg.m_snapshotMktData, m_mktDataOptions);
    }*/
}
