package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;

@Service
public class CashService {
	@Autowired private CashMapper cm;
	
	// CashList AND CashList의 총 합계
	public Map<String, Object> getCashLisyByDate(Cash c) {
		List<Cash> list = cm.selectCashListByDay(c);
		Integer cashKindSum = cm.selectCashKindSum(c);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
	// CashDelete
	public int removeCash(int cashNo) {
		return cm.deleteCash(cashNo);
	}
	// CashSelectOne
	public Map<String, Object> getCashOne(int cashNo) {
		Cash cash = cm.selectCashOne(cashNo);
		List<Category> category = cm.selectCategory();
		System.out.println(category + " <-- CashService.getCashOne.category");
		Map<String, Object> map = new HashMap<>();
		map.put("category", category);
		map.put("cash", cash);
		return map;
	}
	// CashUpdate
	public int modifyCash(Cash c) {
		return cm.updateCash(c);
	}
}
