package com.aeolus.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.aeolus.account.Account;
import com.aeolus.account.CashAccount;
import com.aeolus.backtesting.BacktestingTool;
import com.aeolus.constant.BarSize;
import com.aeolus.core.SystemBase;
import com.aeolus.resources.data.Quote;
import com.aeolus.resources.manager.ResourceManager;
import com.aeolus.strategy.Strategy;
import com.aeolus.strategy.TradingSignal;
import com.aeolus.strategy.pool.BuyAndHold;
import com.aeolus.util.ContractFactory;
import com.ib.client.Contract;
import com.ib.client.TagValue;

public class Test {
	public static void main(String[] args){
		/*ResourceManager.loadHistoricalDataFromDisk();
		BacktestingTool tool = new BacktestingTool();
		System.out.println(tool.testingStrategy(new BuyAndHold(ContractFactory.stockContract("AAPL"), BarSize.Day1)));*/
		SystemBase base = new SystemBase();
		base.connect();
		base.getM_client().reqRealTimeBars(100, ContractFactory.stockContract("SBUX"),
        		3600,
        		"TRADES", true, new Vector<TagValue>());
		/*CashAccount a = new CashAccount(10000);
		a.openLongPosition("SBUX.STK", 50, 60);
		a.openLongPosition("AAPL.STK", 50, 60);
		a.updatePositionPrice("SBUX.STK", 50);
		a.updatePositionPrice("AAPL.STK", 50);
		System.out.println(a.accountInfo());*/
		//ResourceManager.loadHistoricalDataFromDisk();
		/*Strategy s = new Strategy(){
			@Override
			protected List<TradingSignal> checkSignal(List<Contract> contracts, List<List<Quote>> quoteList,
					int currentCursor) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		List<Contract> l = new ArrayList<Contract>();
		l.add(ContractFactory.stockContract("AAPL"));
		l.add(ContractFactory.stockContract("SBUX"));
		//s.feedData(l, BarSize.Day1);
		s.printData();*/
	}
}
