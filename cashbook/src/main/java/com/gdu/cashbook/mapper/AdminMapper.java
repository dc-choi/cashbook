package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Qna;

@Mapper
public interface AdminMapper {
	public Admin selectAdmin(Admin a);
	public List<Qna> selectQnaListAll(String searchWord);
	public List<Member> selectMemberAll();
	public int deleteMember(String memberId);
	public int insertCategory(Category c);
	public int deleteQna(int qnaNo);
	public int updateCommentAndType(Qna q);
}
