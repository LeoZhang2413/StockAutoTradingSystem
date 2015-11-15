package com.aeolus.backtesting;

import com.aeolus.account.Account;
import com.aeolus.account.CashAccount;
import com.aeolus.strategy.Strategy;
import com.aeolus.strategy.TradingSignal;
import com.aeolus.util.MyUtil;
import com.ib.client.Contract;

public class BacktestingTool {
	public BacktestReport testingStrategy(Strategy strategy){
		BacktestReport report = new BacktestReport(1000000,strategy.getStartDate(),strategy.getEndDate());
		while(strategy.hasNextCursor()){
			strategy.nextCursor();
			report.getFinalAccount().updatePositionPrice(strategy.getQuoteAtCurrentCursor());
			for(TradingSignal signal:strategy.checkSignal()){
				report.getFinalAccount().excuteSignal(signal);
			}
		}
		return report;
	}
}
