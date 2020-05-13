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
	
	public String checkMemberId(String memberIdCheck) {
		return mm.selectMemberId(memberIdCheck);
	}
	public int addMember(Member m) {
		return mm.insertMember(m);
	}
	public LoginMember LoginMember(LoginMember lm) {
		return mm.selectLoginMember(lm);
	}
}