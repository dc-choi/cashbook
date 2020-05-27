package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.AdminMapper;
import com.gdu.cashbook.mapper.QnaMapper;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Qna;

@Service
@Transactional
public class AdminService {
	@Autowired private AdminMapper am;
	@Autowired private QnaMapper qm;
	
	public Admin getAdmin(Admin a) {
		return am.selectAdmin(a);
	}
	public List<Qna> getQnaListAll() {
		return am.selectQnaListAll();
	}
	public List<Member> getMemberAll() {
		return am.selectMemberAll();
	}
	public int removeMember(String memberId) {
		return am.deleteMember(memberId);
	}
	public Qna getQnaListOne(int qnaNo) {
		return qm.selectQnaListOne(qnaNo);
	}
	public int addCategory(Category c) {
		return am.insertCategory(c);
	}
	public int removeQna(int qnaNo) {
		return am.deleteQna(qnaNo);
	}
	public int modifyCommentAndType(Qna q) {
		return am.updateCommentAndType(q);
	}
}
