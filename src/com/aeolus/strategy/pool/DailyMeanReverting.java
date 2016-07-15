package com.aeolus.strategy.pool;

import java.util.List;

import com.aeolus.account.Account;
import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.Quote;
import com.aeolus.strategy.MonosecurityStrategy;
import com.aeolus.strategy.SignalType;
import com.aeolus.strategy.TradingSignal;
import com.ib.client.Contract;

public class DailyMeanReverting extends MonosecurityStrategy{

	public DailyMeanReverting(Account account, Contract contract, BarSize barsize) {
		super(account, contract, barsize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TradingSignal checkSignal(Contract contract, List<Quote> quotes, int currentCursor) {
		if((quotes.get(currentCursor).getClose()-quotes.get(currentCursor).getOpen())/quotes.get(currentCursor).getOpen()>0.01){
			return new TradingSignal(SignalType.SELL2CLOSE, contract, quotes.get(currentCursor).getTime(), 100000000, quotes.get(currentCursor).getClose());
		}else if((quotes.get(currentCursor).getClose()-quotes.get(currentCursor).getOpen())/quotes.get(currentCursor).getOpen()<-0.01){
			return new TradingSignal(SignalType.BUY2OPEN, contract, quotes.get(currentCursor).getTime(), 100000000, quotes.get(currentCursor).getClose());
		}else{
			return new TradingSignal(SignalType.HOLD, contract, quotes.get(currentCursor).getTime(), 100000000, quotes.get(currentCursor).getClose());
		}
	}

}
