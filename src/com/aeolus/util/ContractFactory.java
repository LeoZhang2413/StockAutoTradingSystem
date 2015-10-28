package com.aeolus.util;

import com.ib.client.Contract;

public class ContractFactory {
	public static Contract stockContract(String stockSymbol){
		Contract m_contract = new Contract();
        m_contract.m_symbol = stockSymbol;
        m_contract.m_secType = "STK";
        m_contract.m_exchange = "SMART";
        m_contract.m_primaryExch = "ISLAND";
        m_contract.m_currency = "USD";
        return m_contract;
	}
}
