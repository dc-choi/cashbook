package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService ms;
	
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addMember(Member m) {
		ms.addMember(m);
		// toString()을 이용하여 모든 VO값을 출력한다.
		System.out.println(m.toString());
		return "redirect:/index";
	}
}