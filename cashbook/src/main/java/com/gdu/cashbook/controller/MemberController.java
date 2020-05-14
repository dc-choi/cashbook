package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService ms;
	
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		// 로그인 중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addMember(Member m, HttpSession session) {
		// 로그인 중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		ms.addMember(m);
		// toString()을 이용하여 모든 VO값을 출력한다.
		System.out.println(m.toString());
		return "redirect:/index";
	}
	@GetMapping("/login")
	public String login(HttpSession session) {
		// 로그인 중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		return "login";
	}
	@PostMapping("/login")
	public String login(Model model, LoginMember lm, HttpSession session) { // 스프링에서 세션을 줄때 매개변수로 가져온다고 한다
		// 로그인 중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		System.out.println(lm.toString());
		// 값이 없을경우 null이 반환됨
		LoginMember LM = ms.LoginMember(lm);
		if(LM == null) { // 실패
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			return "login";
		} else { // 성공
			session.setAttribute("LM", LM);
			return "redirect:/home";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 로그인 아닐때
		if(session.getAttribute("LM") == null) {
			return "redirect:/";
		}
		session.invalidate();
		return "redirect:/index";
	}
	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck") String memberIdCheck) {
		// 로그인 중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		String confirmMemberId = ms.checkMemberId(memberIdCheck);
		System.out.println(confirmMemberId);
		if(confirmMemberId == null) {
			System.out.println("아이디를 사용할 수 있습니다");
			model.addAttribute("msg", "사용할 수 있는 아이디입니다");
			model.addAttribute("memberIdCheck", memberIdCheck);
		} else {
			System.out.println("아이디를 사용할 수 없습니다");
			model.addAttribute("msg", "사용중인 아이디입니다");
		}
		return "addMember";
	}
	@GetMapping("/memberInfo")
	public String getMemberInfo(HttpSession session, Model model) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/login";
		}
		Member member = ms.getMemberOne((LoginMember)(session.getAttribute("LM")));
		model.addAttribute("member", member);
		return "memberInfo";
	}
}
