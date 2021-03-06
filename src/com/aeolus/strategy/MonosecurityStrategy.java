package com.aeolus.strategy;

import java.util.ArrayList;
import java.util.List;

import com.aeolus.account.Account;
import com.aeolus.constant.BarSize;
import com.aeolus.resources.data.Quote;
import com.ib.client.Contract;

public abstract class MonosecurityStrategy extends Strategy{
	public MonosecurityStrategy(Account account, final Contract contract, BarSize barsize){
		super(account);
		List<Contract> contracts = new ArrayList<Contract>();
		contracts.add(contract);
		feedDataForBacktesting(contracts,barsize);
	}
	@Override
	public List<TradingSignal> checkSignal(List<Contract> contracts, List<List<Quote>> quoteList,
			int currentCursor) {
		List<TradingSignal> signals = new ArrayList<TradingSignal>();
		signals.add(checkSignal(contracts.get(0),quoteList.get(0),currentCursor));
		return signals;
	}
	abstract public TradingSignal checkSignal(Contract contract, List<Quote> quotes, int currentCursor);
}
