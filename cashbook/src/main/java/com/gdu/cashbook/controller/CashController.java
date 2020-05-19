package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cs;
	
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/index";
		}
		// 현재 날짜를 데이터 타입에 맞게 포멧을 변경한다
		LocalDate now = LocalDate.now();
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("YYYY-MM-d");
		String strToday = "";
		strToday = dateTime.format(now);
		System.out.println(strToday + " <-- CashController.getCashListByDate.strToday");
		
		String loginMemberId = ((LoginMember)(session.getAttribute("LM"))).getMemberId();
		System.out.println(loginMemberId + " <-- CashController.getCashListByDate.loginMemberId");
		
		Cash c = new Cash();
		c.setMemberId(loginMemberId);
		c.setCashDate(strToday);
		
		List<Cash> list = cs.getCashLisyByDate(c);
		model.addAttribute("list", list);
		
		for(Cash c2 : list) {
			System.out.println(c2);
		}
		
		return "getCashListByDate";
	}
}
