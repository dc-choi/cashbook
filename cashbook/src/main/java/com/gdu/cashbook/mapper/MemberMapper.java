package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	public Member selectMemberOne(LoginMember lm);
	public String selectMemberId(String memberIdCheck);
	public String IdCk(String memberId, String memberPw);
	public int deleteMember(String memberId);
	public int updateMember(Member m);
	public int insertMember(Member m);
	public LoginMember selectLoginMember(LoginMember lm);
}
