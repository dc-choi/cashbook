package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.vo.Admin;

@Controller
public class AdminComtroller {
	@Autowired private AdminService as;
	
	@GetMapping("/adminLogin")
	public String adminLogin(HttpSession session) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		return "adminLogin";
	}
	@PostMapping("/adminLogin")
	public String adminLogin(HttpSession session, Model model, Admin a) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		System.out.println(a + " <-- adminLogin.a");
		
		Admin admin = as.getAdmin(a);
		System.out.println(admin + " <-- adminLogin.admin");
		
		if(admin == null) {
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			return "adminLogin";
		} else {
			session.setAttribute("admin", admin);
			return "admin";
		}
	}
}