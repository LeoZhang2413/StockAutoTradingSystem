package com.aeolus.backtesting;

import com.aeolus.strategy.Strategy;
import com.aeolus.strategy.TradingSignal;

public class BacktestingTool {
	public BacktestReport testingStrategy(Strategy strategy){
		BacktestReport report = new BacktestReport(strategy);
		while(strategy.hasNextCursor()){
			strategy.nextCursor();
			strategy.getAccount().updatePositionPrice(strategy.getQuoteAtCurrentCursor());
			report.recordNetworth(strategy.getDateAtCurrentCursor(), strategy.getAccount().currentNetworth());
			for(TradingSignal signal:strategy.checkSignal()){
				strategy.getAccount().excuteSignal(signal);
				//report.recordSignal(signal);
			}
		}
		report.setAnnualSharpeRatio(StatisticsCalculator.sharpeRatioFromDailyData(report.getNetworth(), 0.01));
		return report;
	}
}
