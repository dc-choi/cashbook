package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.MonthAndPrice;

@Mapper
public interface CashMapper {
	// CashList
	public List<Cash> selectCashListByDay(Cash c);
	// CashList의 총 합계
	public Integer selectCashKindSum(Cash c);
	// CashList By Month in DayAndPrice
	public List<DayAndPrice> selectDayAndPrice(Map<String, Object> map);
	// CashList By Month in MonthAndPrice
	public List<MonthAndPrice> selectMonthAndPrice(Map<String, Object> map);
	// CashSelectOne
	public Cash selectCashOne(int cashNo);
	// selectCategory AND CashInsertForm
	public List<Category> selectCategory();
	// CashInsert
	public int insertCash(Cash c);
	// CashDelete
	public int deleteCash(int cashNo);
	// CashUpdate
	public int updateCash(Cash c);
}
