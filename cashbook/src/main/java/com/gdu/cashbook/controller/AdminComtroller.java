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

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Qna;

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
			System.out.println(admin.getAdminId() + " <-- adminLogin.admin.getAdminId()");
			session.setAttribute("admin", admin);
			return "redirect:/admin";
		}
	}
	@GetMapping("/adminLogout")
	public String adminLogout(HttpSession session) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		session.invalidate();
		
		return "redirect:/";
	}
	@GetMapping("/admin")
	public String admin(HttpSession session) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		return "admin";
	}
	@GetMapping("/addCategory")
	public String addCategory(HttpSession session) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		return "addCategory";
	}
	@PostMapping("/addCategory")
	public String addCategory(HttpSession session, RedirectAttributes redirectAttributes, Category c) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		System.out.println(c + " <-- addCategory.c");
		
		int count = as.addCategory(c);
		System.out.println(count);
		
		if(count == 1) {
			String msg = "Category가 입력되었습니다.";
			// GET방식이 아닌 POST방식으로 매개변수를 보내려면 addFlashAttribute 사용해야함
			// 그러나 1회성이다...
			redirectAttributes.addFlashAttribute("msg", msg);
		}
		return "redirect:/admin";
	}
	@GetMapping("/adminMemberManagement")
	public String getMemberAll(HttpSession session, Model model) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		List<Member> list = as.getMemberAll();
		
		model.addAttribute("list", list);
		return "adminMemberManagement";
	}
	@GetMapping("/adminRemoveMember")
	public String adminRemoveMember(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam(value = "memberId") String memberId) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		int count = as.removeMember(memberId);
		
		if(count == 1) {
			String msg = "멤버를 삭제하였습니다.";
			// GET방식이 아닌 POST방식으로 매개변수를 보내려면 addFlashAttribute 사용해야함
			// 그러나 1회성이다...
			redirectAttributes.addFlashAttribute("msg", msg);
		}
		return "redirect:/adminMemberManagement";
	}
	@GetMapping("/adminQnaList")
	public String adminQnaList(HttpSession session, Model model, @RequestParam(value = "searchWord", defaultValue = "") String searchWord) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		List<Qna> list = as.getQnaListAll(searchWord);
		
		model.addAttribute("list", list);
		return "adminQnaList";
	}
	@GetMapping("/adminQnaListOne")
	public String adminQnaListOne(HttpSession session, Model model, @RequestParam(value = "qnaNo") int qnaNo) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		Qna q = as.getQnaListOne(qnaNo);
		
		model.addAttribute("q", q);
		return "adminQnaListOne";
	}
	@PostMapping("/adminQnaListOne")
	public String adminQnaListOne(HttpSession session, Qna q, RedirectAttributes redirectAttributes) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		int count = as.modifyCommentAndType(q);
		
		if(count == 1) {
			String msg = "답글이 입력되었습니다.";
			// GET방식이 아닌 POST방식으로 매개변수를 보내려면 addFlashAttribute 사용해야함
			// 그러나 1회성이다...
			redirectAttributes.addFlashAttribute("msg", msg);
		}
		return "redirect:/adminQnaList";
	}
	@GetMapping("/removeQna")
	public String removeQna(HttpSession session, @RequestParam(value = "qnaNo") int qnaNo, RedirectAttributes redirectAttributes) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		} else if (session.getAttribute("admin") == null) {
			System.out.println(session.getAttribute("admin") + " <-- session is null");
			return "redirect:/";
		}
		int count = as.removeQna(qnaNo);
		
		if(count == 1) {
			String msg = "게시글을 삭제하였습니다.";
			// GET방식이 아닌 POST방식으로 매개변수를 보내려면 addFlashAttribute 사용해야함
			// 그러나 1회성이다...
			redirectAttributes.addFlashAttribute("msg", msg);
		}
		return "redirect:/adminQnaList";
	}
}



