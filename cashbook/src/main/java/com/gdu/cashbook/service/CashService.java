package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.MonthAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cm;
	
	// CashList AND CashList의 총 합계
	public Map<String, Object> getCashLisyByDate(Cash c) {
		List<Cash> list = cm.selectCashListByDay(c);
		Integer cashKindSum = cm.selectCashKindSum(c);
		System.out.println(cashKindSum + " <-- CashService.getCashLisyByDate.cashKindSum");
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
	// CashList By Month in DayAndPrice
	public List<DayAndPrice> getCashAndPriceList(String memberId, int year, int month) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cm.selectDayAndPrice(map);
	}
	// CashList By Month in DayAndPrice
		public List<MonthAndPrice> getCashAndPriceList2(String memberId, int year, int month) {
			Map<String, Object> map = new HashMap<>();
			map.put("memberId", memberId);
			map.put("year", year);
			map.put("month", month);
			return cm.selectMonthAndPrice(map);
		}
	// CashInsertForm
	public List<Category> getCashInsertForm() {
		List<Category> category = cm.selectCategory();
		return category;
	}
	public int addCash(Cash c) {
		return cm.insertCash(c);
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
