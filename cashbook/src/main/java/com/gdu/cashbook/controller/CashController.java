package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cs;
	
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
		
		List<Cash> list = cs.getCashLisyByDate(c);
		System.out.println(list.size() + " <-- CashController.getCashListByDate.list.size()");
		
		model.addAttribute("list", list);
		model.addAttribute("now", now);
		
		for(Cash c2 : list) {
			System.out.println(c2);
		}
		return "getCashListByDate";
	}
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session, @RequestParam(value="cashNo") int cashNo) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		System.out.println(cashNo + " <-- CashController.removeCash.cashNo");
		cs.removeCash(cashNo);
		return "redirect:/getCashListByDate";
	}
}