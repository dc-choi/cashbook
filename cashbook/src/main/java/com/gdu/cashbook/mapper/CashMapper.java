package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	// CashList
	public List<Cash> selectCashListByDay(Cash c);
	// CashList의 총 합계
	public Integer selectCashKindSum(Cash c);
	// CashList By Month
	public List<DayAndPrice> selectDayAndPrice(Map<String, Object> map);
	// CashSelectOne
	public Cash selectCashOne(int cashNo);
	// selectCategory
	public List<Category> selectCategory();
	// CashDelete
	public int deleteCash(int cashNo);
	// CashUpdate
	public int updateCash(Cash c);
}
