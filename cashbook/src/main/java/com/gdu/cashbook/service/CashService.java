package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;

@Service
public class CashService {
	@Autowired private CashMapper cm;
	public List<Cash> getCashLisyByDate(Cash c) {
		return cm.selectCashListByDay(c);
	}
}
