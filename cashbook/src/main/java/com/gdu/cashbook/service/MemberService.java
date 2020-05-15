package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Service
@Transactional
public class MemberService {
	
	@Autowired private MemberMapper mm;
	@Autowired private JavaMailSender mail; // @Conponent으로 객체를 생성해야함 하지만 그건 사용불가하므로 직접 Bean을 생성해야한다.
	
	public Member getMemberOne(LoginMember lm) {
		return mm.selectMemberOne(lm);
	}
	public String checkMemberId(String memberIdCheck) {
		return mm.selectMemberId(memberIdCheck);
	}
	public int RemoveMember(String memberId, String memberPw) {
		int count = 0;
		String check = mm.IdCk(memberId, memberPw);
		if(check == null) {
			return count;
		}
		count = mm.deleteMember(memberId);
		return count;
	}
	public int modifyMember(Member m) {
		int count = 0;
		String check = mm.IdCk(m.getMemberId(), m.getMemberPw());
		if(check == null) {
			return count;
		}
		count = mm.updateMember(m);
		return count;
	}
	public int addMember(Member m) {
		return mm.insertMember(m);
	}
	public LoginMember LoginMember(LoginMember lm) {
		return mm.selectLoginMember(lm);
	}
	public String getMemberIdByMember(Member m) {
		return mm.selectMemberIdByMember(m);
	}
	public int getMemberPw(Member m) {
		// pw를 생성하는 객체 생성
		UUID u = UUID.randomUUID();
		String memberPw = u.toString().substring(0, 8);
		m.setMemberPw(memberPw);
		int row = mm.updateMemberPw(m);
		if(row == 1) {
			System.out.println(memberPw + " <-- MemberService.getMemberPw.memberPw");
			System.out.println(m.getMemberEmail());
			// 메일로 update 성공한 랜덤 pw를 제공
			SimpleMailMessage sm = new SimpleMailMessage();
			sm.setTo(m.getMemberEmail());
			sm.setFrom("ddagae0805@gmail.com");
			sm.setSubject("갓재우 가라사대 너가 패스워드를 찾고있느냐");
			sm.setText("보라 여기 너가 찾던 패스워드다. " + " " + memberPw);
			mail.send(sm);
		}
		return row;
	}
}