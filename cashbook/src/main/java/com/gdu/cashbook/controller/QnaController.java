package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.cashbook.service.QnaService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Qna;

@Controller
public class QnaController {
	@Autowired private QnaService qs;
	
	@GetMapping("/qnaList")
	public String qnaList(HttpSession session, Model model) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/";
		}
		String loginMemberId = ((LoginMember)(session.getAttribute("LM"))).getMemberId();
		System.out.println(loginMemberId + " <-- QnaController.qnaList.loginMemberId");
		
		List<Qna> list = qs.getQnaListAll(loginMemberId);
		
		model.addAttribute("list", list);
		model.addAttribute("memberId", loginMemberId);
		
		return "qnaList";
	}
	@GetMapping("/qnaListOne")
	public String qnaListOne(HttpSession session, Model model, @RequestParam(value = "qnaNo") int qnaNo) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/";
		}
		String loginMemberId = ((LoginMember)(session.getAttribute("LM"))).getMemberId();
		System.out.println(loginMemberId + " <-- QnaController.qnaListOne.loginMemberId");
		
		Qna q = qs.getQnaListOne(qnaNo);
		
		model.addAttribute("qua", q);
		model.addAttribute("memberId", loginMemberId);
		model.addAttribute("qnaNo", qnaNo);
		
		return "qnaListOne";
	}
	@GetMapping("/addQna")
	public String addQna(HttpSession session, Model model) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/";
		}
		String loginMemberId = ((LoginMember)(session.getAttribute("LM"))).getMemberId();
		model.addAttribute("memberId", loginMemberId);
		
		return "addQna";
	}
	@PostMapping("/addQna")
	public String addQna(HttpSession session, Qna q) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/";
		}
		System.out.println(q + " <-- QnaController.addQna.q");
		
		qs.addQna(q);
		return "redirect:/qnaList";
	}
	@GetMapping("/modifyQna")
	public String modifyQna(HttpSession session, Model model, @RequestParam(value = "qnaNo") int qnaNo) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/";
		}
		
		Qna q = qs.getQnaListOne(qnaNo);
		System.out.println(q + " <-- modifyQna.q");
		
		model.addAttribute("q", q);
		model.addAttribute("qnaNo", qnaNo);
		
		return "modifyQna";
	}
	@PostMapping("/modifyQna")
	public String modifyQna(HttpSession session, Qna q, RedirectAttributes redirectAttributes) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/";
		}
		System.out.println(q + " <-- modifyQna.q");
		
		qs.modifyQna(q);
		
		// redirect시에는 Model로 매개변수를 보낼수가 없다.
		redirectAttributes.addAttribute("qnaNo", q.getQnaNo());
		return "redirect:/qnaListOne";
	}
}




