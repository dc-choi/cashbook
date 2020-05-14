package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper mm;
	
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
}