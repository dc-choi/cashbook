package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;

@Controller
public class MemberController {
	@Autowired private MemberService ms;
	private MultipartFile mfFile;
	
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		// 로그인 중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addMember(HttpSession session, MemberForm mf) {
		// 로그인 중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/";
		}
		System.out.println(mf.toString());
		mfFile = mf.getMemberPic();
		String originName = mfFile.getOriginalFilename();
		System.out.println(originName + " <-- MemberController.addMember.originName");

		if(!originName.equals("")) {
			if(!mf.getMemberPic().getContentType().equals("image/png")
				&& !mf.getMemberPic().getContentType().equals("image/jpg")
				&& !mf.getMemberPic().getContentType().equals("image/jpeg")
				&& !mf.getMemberPic().getContentType().equals("image/gif")) {
				System.out.println("이미지 파일만 업로드해주세요");
				return "redirect:/addMember";
			}
		}
		ms.addMember(mf);
		// toString()을 이용하여 모든 VO값을 출력한다.
		
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
			session.setAttribute("PW", lm.getMemberPw());
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
			model.addAttribute("msg", "사용중인 아이디 또는 탈퇴한 아이디입니다.");
		}
		return "addMember";
	}
	@GetMapping("/memberInfo")
	public String getMemberInfo(HttpSession session, Model model) {
		// 로그인 아닐때
		if(session.getAttribute("LM") == null) {
			return "redirect:/login";
		}
		Member member = ms.getMemberOne((LoginMember)(session.getAttribute("LM")));
		model.addAttribute("member", member);
		return "memberInfo";
	}
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session) {
		// 로그인 아닐때
		if(session.getAttribute("LM") == null) {
			return "redirect:/login";
		}
		return "removeMember";
	}
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, String memberId, String memberPw) {
		// 로그인 아닐때
		if(session.getAttribute("LM") == null) {
			return "redirect:/login";
		}
		// 정상적으로 삭제되었는지 확인하기 위해서 int값을 받아온다.
		int check = ms.RemoveMember(memberId, memberPw);
		// check값이 0일경우 삭제에 실패
		if(check == 0) {
			System.out.println("삭제를 실패하였습니다.");
			return "redirect:/home";
		} else {
			System.out.println(check);
		}
		return "redirect:/logout";
	}
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model) {
		// 로그인 아닐때
		if(session.getAttribute("LM") == null) {
			return "redirect:/login";
		}
		// 수정폼을 불러오기 위해서 LoginMember로 세션을 형변환후 데이터를 불러온다
		Member member = ms.getMemberOne((LoginMember)(session.getAttribute("LM")));
		model.addAttribute("member", member);
		return "modifyMember";
	}
	@PostMapping("/modifyMember")
	public String modifyMember(HttpSession session, MemberForm mf) {
		// 로그인 아닐때
		if(session.getAttribute("LM") == null) {
			return "redirect:/login";
		}
		// 받아온 PW의 값이 맞는지 확인해본다
		String pw = ((String)(session.getAttribute("PW")));
		String pw2 = ((String)(mf.getMemberPw()));
		// 디버그코드
		System.out.println(pw + " <-- MemberController.modifyMember.pw");
		System.out.println(pw2 + " <-- MemberController.modifyMember.pw2");
		System.out.println(pw.equals(pw2));
		// pw와 pw2가 같다면 실행하고 아니면 오류를 출력한다.
		if(pw.equals(pw2)) {
			// 정상적으로 수정되었는지 확인하기 위해서 int값을 받아온다.
			int check = ms.modifyMember(mf);
			// check값이 0일경우 수정 실패
			if(check == 0) {
				System.out.println("두번째에서 수정 실패하였습니다.");
				return "redirect:/home";
			} else {
				System.out.println(check);
			}
			return "redirect:/home";
		} else {
			System.out.println("첫번째에서 수정 실패하였습니다.");
			return "redirect:/home";
		}
	}
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		// 로그인중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/login";
		}
		return "findMemberId";
	}
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member m) {
		// 로그인중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/login";
		}
		String memberId = ms.getMemberIdByMember(m);
		System.out.println(memberId + " <-- MemberController.findMemberId.memberId");
		model.addAttribute("memberId", memberId);
		return "memberIdView";
	}
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		// 로그인중일때
		if(session.getAttribute("LM") != null) {
			return "redirect:/login";
		}
		return "findMemberPw";
	}
	@PostMapping("findMemberPw")
	public String findMemberPw(HttpSession session, Model model, Member m) {
		int row = ms.getMemberPw(m);
		String msg = "ID와 Email을 똑바로 확인하세요";
		if(row == 1) {
			msg = "입력된 Email로 Pw를 보냈습니다";
		}
		model.addAttribute("msg", msg);
		return "memberPwView";
	}
}

