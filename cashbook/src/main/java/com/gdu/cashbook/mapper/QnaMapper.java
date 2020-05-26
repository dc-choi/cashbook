package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Qna;

@Mapper
public interface QnaMapper {
	public List<Qna> selectQnaListAll(String memberId);
	public Qna selectQnaListOne(int QnaNo);
	public int insertQna(Qna q);
	public int updateQna(Qna q);
}
