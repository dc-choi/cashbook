package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.QnaMapper;
import com.gdu.cashbook.vo.Qna;

@Service
@Transactional
public class QnaService {
	@Autowired private QnaMapper qm;
	
	public List<Qna> getQnaListAll(String memberId) {
		return qm.selectQnaListAll(memberId);
	}
	public Qna getQnaListOne(int qnaNo) {
		return qm.selectQnaListOne(qnaNo);
	}
	public int addQna(Qna q) {
		return qm.insertQna(q);
	}
	public int modifyQna(Qna q) {
		return qm.updateQna(q);
	}
}
