package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.MonthAndPrice;

@Controller
public class CashController {
	@Autowired private CashService cs;
	
	// CashListByMonth AND CashListByMonth의 총 합계
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, @RequestParam(value="now", required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate now) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		String memberId = ((LoginMember)(session.getAttribute("LM"))).getMemberId();
		Calendar cNow = Calendar.getInstance();
		if(now == null) {
			now = LocalDate.now();
		} else {
			cNow.set(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		}
		int year = now.getYear();
		int month = now.getMonthValue();
		
		
		Calendar first = cNow;
		first.set(Calendar.DATE, 1);
		int dow = first.get(Calendar.DAY_OF_WEEK);
		int lastday = cNow.getActualMaximum(Calendar.DATE);
		System.out.println(dow + " <-- CashController.getCashListByMonth.dow");
		
		// 일별 수입, 지출 총액
		List<DayAndPrice> dayPriceList = cs.getCashAndPriceList(memberId, year, month);
		// 디버그 코드
		for(DayAndPrice dp : dayPriceList) {
			System.out.println(dp);
		}
		
		List<MonthAndPrice> monthPriceList = cs.getCashAndPriceList2(memberId, year, month);
		// 디버그 코드
		for(MonthAndPrice mp : monthPriceList) {
			System.out.println(mp);
		}
		
		model.addAttribute("now", now);
		model.addAttribute("month", month);
		model.addAttribute("dow", dow);
		model.addAttribute("lastday", lastday);
		model.addAttribute("dayPriceList", dayPriceList);
		model.addAttribute("monthPriceList", monthPriceList);
		return "getCashListByMonth";
	}
	// CashListByDate AND CashListByDate의 총 합계
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value="now", required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate now) {
		
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		/*
		LocalDateTime, DateTimeFormatter와 같은 클래스들은 String 클래스처럼 변경이 불가능하다.
		즉, 날짜나 시간을 변경하면 기존의 객체가 변경되는 것이 아니라, 새로운 객체를 반환한다.
		기존의 SimpleDateFormat, Calendar 클래스는 기존의 객체에서 변경가능 하므로 멀티쓰레드 환경에서 안전하지 않았다.

		멀티쓰레드 환경에서는 여러 쓰레드가 동시에 같은 객체에 접근할 수 있어서 변경 가능한 객체의 데이터가 잘못 될 가능성이 있다.
		이를 쓰레드 안전(Thread-safe)하지 못하다고 한다.
		 */
		
		// 현재 날짜를 데이터 타입에 맞게 포멧을 변경한다
		if(now == null) {
			now = LocalDate.now();
		}
		System.out.println(now + " <-- CashController.getCashListByDate.now");
		
		// sysout 했을시에 값은 같지만 데이터 타입이 달라 String으로 변환해줘야 한다.
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String strToday = "";
		strToday = dateTime.format(now);
		System.out.println(strToday + " <-- CashController.getCashListByDate.strToday");
		
		String loginMemberId = ((LoginMember)(session.getAttribute("LM"))).getMemberId();
		System.out.println(loginMemberId + " <-- CashController.getCashListByDate.loginMemberId");
		
		Cash c = new Cash();
		c.setMemberId(loginMemberId);
		c.setCashDate(strToday);
		
		Map<String, Object> map = cs.getCashLisyByDate(c);
		
		Integer cashKindSum = (Integer)map.get("cashKindSum");
		
		if(cashKindSum == null) {
			cashKindSum = 0;
		}
		System.out.println(cashKindSum + " <-- CashController.getCashListByDate.check");
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("cashKindSum", cashKindSum);
		model.addAttribute("now", now);
		/*
		for(Cash c2 : list) {
			System.out.println(c2);
		}
		*/
		return "getCashListByDate";
	}
	// CashInsertForm
	@GetMapping("/addCashList")
	public String addCash(HttpSession session, Model model) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		List<Category> category = cs.getCashInsertForm();
		model.addAttribute("category", category);
		return "addCashList";
	}
	// CashInsert
	@PostMapping("/addCash")
	public String addCash(HttpSession session, Cash c) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		// 디버그 코드
		String loginMemberId = ((LoginMember)(session.getAttribute("LM"))).getMemberId();
		c.setMemberId(loginMemberId);
		
		System.out.println(c.getCashNo() + " <-- CashController.addCash.c.getCashNo()");
		System.out.println(c.getCashKind() + " <-- CashController.addCash.c.getCashKind()");
		System.out.println(c.getCategoryName() + " <-- CashController.addCash.c.getCategoryName()");
		System.out.println(c.getMemberId() + " <-- CashController.addCash.c.getMemberId()");
		System.out.println(c.getCashPrice() + " <-- CashController.addCash.c.getCashPrice()");
		System.out.println(c.getCashPlace() + " <-- CashController.addCash.c.getCashPlace()");
		System.out.println(c.getCashMemo() + " <-- CashController.addCash.c.getCashMemo()");
		System.out.println(c.getCashDate() + " <-- CashController.addCash.c.getCashDate()");
		
		cs.addCash(c);
		
		return "redirect:/getCashListByDate";
	}
	// CashDelete
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session, @RequestParam(value="cashNo") int cashNo) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		System.out.println(cashNo + " <-- CashController.removeCash.cashNo");
		cs.removeCash(cashNo);
		return "redirect:/getCashListByDate";
	}
	// CashSelectOne
	@GetMapping("/modifyCash")
	public String modifyCash(HttpSession session, Model model, @RequestParam(value="cashNo") int cashNo) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		System.out.println(cashNo + " <-- CashController.modifyCash.cashNo");
		
		Map<String, Object> map = cs.getCashOne(cashNo);
		
		Cash c = (Cash)map.get("cash");
		
		System.out.println(map.get("category") + " <-- CashController.modifyCash.map.get('category')");
				
		model.addAttribute("category", map.get("category"));
		model.addAttribute("c", c);
		return "modifyCash";
	}
	// CashUpdate
	@PostMapping("/modifyCash")
	public String modifyCash(HttpSession session, Cash c) {
		// 디버그 코드
		System.out.println(c.getCashNo() + " <-- CashController.modifyCash.c.getCashNo()");
		System.out.println(c.getCashKind() + " <-- CashController.modifyCash.c.getCashKind()");
		System.out.println(c.getCategoryName() + " <-- CashController.modifyCash.c.getCategoryName()");
		System.out.println(c.getCashPrice() + " <-- CashController.modifyCash.c.getCashPrice()");
		System.out.println(c.getCashPlace() + " <-- CashController.modifyCash.c.getCashPlace()");
		System.out.println(c.getCashMemo() + " <-- CashController.modifyCash.c.getCashMemo()");
		System.out.println(c.getCashDate() + " <-- CashController.modifyCash.c.getCashDate()");
		
		cs.modifyCash(c);
		
		return "redirect:/getCashListByDate";
	}
}






