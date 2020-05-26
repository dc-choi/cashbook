package com.gdu.cashbook.vo;

public class Qna {
	private int qnaNo;
	private String memberId;
	private String qnaTitle;
	private String qnaContent;
	private String qnaComment;
	private String qnaType;
	private String qnaDate;
	
	public int getQnaNo() {
		return qnaNo;
	}
	public void setQnaNo(int qnaNo) {
		this.qnaNo = qnaNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getQnaTitle() {
		return qnaTitle;
	}
	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}
	public String getQnaContent() {
		return qnaContent;
	}
	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}
	public String getQnaComment() {
		return qnaComment;
	}
	public void setQnaComment(String qnaComment) {
		this.qnaComment = qnaComment;
	}
	public String getQnaType() {
		return qnaType;
	}
	public void setQnaType(String qnaType) {
		this.qnaType = qnaType;
	}
	public String getQnaDate() {
		return qnaDate;
	}
	public void setQnaDate(String qnaDate) {
		this.qnaDate = qnaDate;
	}
	
	@Override
	public String toString() {
		return "Qna [qnaNo=" + qnaNo + ", memberId=" + memberId + ", qnaTitle=" + qnaTitle + ", qnaContent="
				+ qnaContent + ", qnaComment=" + qnaComment + ", qnaType=" + qnaType + ", qnaDate=" + qnaDate + "]";
	}
}
