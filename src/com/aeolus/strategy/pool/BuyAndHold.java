package com.aeolus.strategy.pool;

import java.util.List;

import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.Quote;
import com.aeolus.strategy.MonosecurityStrategy;
import com.aeolus.strategy.SignalType;
import com.aeolus.strategy.TradingSignal;
import com.ib.client.Contract;

public class BuyAndHold extends MonosecurityStrategy{

	public BuyAndHold(Contract contract, BarSize barsize) {
		super(contract, barsize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TradingSignal checkSignal(Contract contract, List<Quote> quotes, int currentCursor) {
		// TODO Auto-generated method stub
		if(currentCursor == 0){
			return new TradingSignal(SignalType.BUY, contract, quotes.get(currentCursor).getTime(), 1, quotes.get(currentCursor).getClose());
		}else{
			return new TradingSignal(SignalType.HOLD, contract, quotes.get(currentCursor).getTime(), 0,quotes.get(currentCursor).getClose());
		}
	}
	
}
