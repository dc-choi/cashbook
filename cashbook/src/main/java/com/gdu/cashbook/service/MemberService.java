package com.gdu.cashbook.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;

@Service
@Transactional
public class MemberService {
	
	@Autowired private MemberMapper mm;
	@Autowired private JavaMailSender mail; // @Conponent으로 객체를 생성해야함 하지만 그건 사용불가하므로 직접 Bean을 생성해야한다.
	@Value("D:\\git-cashbook\\cashbook\\cashbook\\src\\main\\resources\\static\\upload\\")
	private String path;
	private MultipartFile mfFile;
	
	public Member getMemberOne(LoginMember lm) {
		return mm.selectMemberOne(lm);
	}
	public String checkMemberId(String memberIdCheck) {
		return mm.selectMemberId(memberIdCheck);
	}
	public int RemoveMember(String memberId, String memberPw) {
		int count = 0;
		String memberPic = mm.selectMemberPic(memberId);
		System.out.println(memberPic + " <-- MemberService.RemoveMember.memberPic");
		File file = new File(path + memberPic);
		
		if(memberPic.equals("default.jpg")) {
			System.out.println("기본 이미지만 올렸던 유저입니다.");
		} else {
			if(file.exists()) {
				file.delete();
			}
		}
		String check = mm.IdCk(memberId, memberPw);
		if(check == null) {
			return count;
		}
		count = mm.deleteMember(memberId);
		return count;
	}
	public int modifyMember(MemberForm mf) {
		int count = 0;
		String check = mm.IdCk(mf.getMemberId(), mf.getMemberPw());
		if(check == null) {
			return count;
		}
		mfFile = mf.getMemberPic();
		String originName = mfFile.getOriginalFilename();
		System.out.println(originName + " <-- MemberService.modifyMember.originName");
		String memberPic = null;
		
		if(originName.equals("")) {
			memberPic = "default.jpg";
			System.out.println(memberPic + " <-- MemberService.addMember.memberPic");
		} else {
			int lastDot = originName.lastIndexOf(".");
			String ex = originName.substring(lastDot);
			
			memberPic = mf.getMemberId() + ex;
			System.out.println(memberPic + " <-- MemberService.addMember.memberPic");
			
			File file = new File(path + "\\" + memberPic);
			try {
				mfFile.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		
		Member m = new Member();
		m.setMemberId(mf.getMemberId());
		m.setMemberPw(mf.getMemberPw());
		m.setMemberName(mf.getMemberName());
		m.setMemberAddr(mf.getMemberAddr());
		m.setMemberEmail(mf.getMemberEmail());
		m.setMemberPhone(mf.getMemberPhone());
		m.setMemberPic(memberPic);
		
		count = mm.updateMember(m);
		return count;
	}
	public int addMember(MemberForm mf) {
		mfFile = mf.getMemberPic();
		// 파일명을 받아오는 객체
		String originName = mfFile.getOriginalFilename();
		System.out.println(originName + " <-- MemberService.addMember.originName");
		// 새로운 이름을 생성할 변수 선언
		String memberPic = null;
		// 값을 입력하지않았을경우 memberPic을 default로 설정
		if(originName.equals("")) {
			memberPic = "default.jpg";
		} else {
			int lastDot = originName.lastIndexOf(".");
			String ex = originName.substring(lastDot);
			
			memberPic = mf.getMemberId() + ex;
			System.out.println(memberPic + " <-- MemberService.addMember.memberPic");
			
			// 1. 파일을 저장한다 (private으로 생성된 path를 가져온다)
			File file = new File(path + "\\" + memberPic);
			// 파일을 디스크에 물리적으로 저장
			try {
				mfFile.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
				// 1) 예외처리를 해야만 문법적으로 이상없는 예외
				// 2) 예외처리를 코드에서 구현하지 않아도 아무 문제없는 예외 ==> RuntimeException
			}
		}
		// 2. DB에 저장한다
		// MemberForm -> Member
		Member m = new Member();
		m.setMemberId(mf.getMemberId());
		m.setMemberPw(mf.getMemberPw());
		m.setMemberName(mf.getMemberName());
		m.setMemberAddr(mf.getMemberAddr());
		m.setMemberEmail(mf.getMemberEmail());
		m.setMemberPhone(mf.getMemberPhone());
		m.setMemberPic(memberPic);
		
		int row = mm.insertMember(m);
		return row;
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
			sm.setSubject("저만의 가계부계정 패스워드를 수정하였습니다.");
			sm.setText("패스워드를 수정 하셨습니다. : " + memberPw);
			mail.send(sm);
		}
		return row;
	}
}